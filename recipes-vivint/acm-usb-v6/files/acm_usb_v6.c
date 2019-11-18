#include <stdio.h>
#include <string.h>
#include <errno.h>

#include <dirent.h>
#include <sys/param.h>

#include <time.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <assert.h>
#include <libusb-1.0/libusb.h>
#include <signal.h>
#include <fcntl.h>
#include <ctype.h>

#if defined FLASH_DEVICE
#define ACM_VID     0x8087
#define ACM_PID     0x0716
#endif

#if 1 || defined EM37345_DEVICE
#define ACM_VID     0x1519
#define ACM_PID     0x0443
#endif

#define is_bulk_endpoint(attr) ((attr & LIBUSB_TRANSFER_TYPE_MASK) == LIBUSB_TRANSFER_TYPE_BULK)

libusb_context *ctx = NULL;
libusb_device_handle *dev_handle;
int   interface_no, ep_in, ep_out, max_out_size = 2048;
int  running=1;
int statistic=0;
int acm_intf=0;
int acm_ctl_intf=0;

/*********************************************************
 * signal handler
 * break the main loop and clean properly
 *********************************************************/
void sig_handler(int signo) {
	if (signo == SIGINT) {
		printf("closing trcrcv nicely\n");
		running = 0;
	} else if (signo == SIGHUP) {
		printf("dump statistic\n");
		statistic = 1;
	}
}

int acm_usb_init()
{
    int r;
    if( libusb_init(&ctx) < 0)
    {
	    printf("Init Error\n");
	    return 1;
	}
	libusb_set_debug(ctx, 3);
	return 0;
}

int acm_usb_open()
{
	printf("Opening VID:PID %04X:%04X ifnum %d ifno %d ep_in %02X ep_out %02X\n",
			ACM_VID, ACM_PID, acm_intf, interface_no, ep_in, ep_out);
    dev_handle = libusb_open_device_with_vid_pid(ctx, ACM_VID, ACM_PID);

	if(dev_handle == NULL)
        return 1;



	if( libusb_kernel_driver_active(dev_handle, acm_intf) == 1)
    {
	    printf("\n\nKernel driver is active\n");
	    if( libusb_detach_kernel_driver(dev_handle, acm_intf) == 0)
	        printf("\n\nKernel driver is detached!\n");
	}
//	if( libusb_claim_interface(dev_handle, acm_ctl_intf) < 0)
//{ printf("\n\n CTL Interface not claimed.\n"); }
	if( libusb_claim_interface(dev_handle, acm_intf) < 0)
	{
	    printf("\n\nInterface not claimed.\n");
	    return 1;
	}
	printf("\n\nInterface claimed.\n");

    return 0;
}
int acm_usb_setup()
{
    /* - set line encoding: here 9600 8N1
     * 9600 = 0x2580 ~> 0x80, 0x25 in little endian
     */
    int rc=0;
    //unsigned char encoding[] = { 0x80, 0x25, 0x00, 0x00, 0x00, 0x00, 0x08 };
    unsigned char encoding[] = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
    rc = libusb_control_transfer(dev_handle, 0x21, 0x22, 3, acm_ctl_intf, encoding,
                                0/*sizeof(encoding)*/, 0);
    if (rc < 0) {
        fprintf(stderr, "Error during control transfer: %s\n",
                libusb_error_name(rc));
        return rc;
    }

    return 0;
}

int acm_usb_write(unsigned char *buf, int buf_len)
{
    int             actual = 0, ret, bytes_left = buf_len;
    unsigned char   *ptr = buf;

    while( bytes_left > max_out_size)
    {
        ret = libusb_bulk_transfer( dev_handle, ep_out, ptr, max_out_size, &actual, 0);
        if( ret == 0)
        {
            ptr += actual;
            bytes_left -= actual;
        }
        else
            return 0;
    }
    if( libusb_bulk_transfer( dev_handle, ep_out, ptr, bytes_left, &actual, 0) == 0)
        return buf_len;

    return 0;
}

int acm_usb_read(unsigned char *buf, int buf_len, unsigned int timeout)
{
    int actual = 0;
    int rc;
    if( (rc=libusb_bulk_transfer( dev_handle, ep_in, buf, buf_len, &actual, timeout) == 0))
    {
        return actual;
	}
    if (rc != 0 /*LIBUSB_ERROR_TIMEOUT */ )
    {
    	fprintf(stderr,"Error at read %s\n", libusb_error_name(rc));
    }
    return 0;
}

void acm_usb_close()
{
    if( dev_handle == NULL)
        return;

    (void)libusb_release_interface( dev_handle, interface_no);
    (void)libusb_attach_kernel_driver( dev_handle, interface_no);


    libusb_close(dev_handle);
}

void acm_usb_deinit()
{
    if( ctx != NULL)
        libusb_exit(ctx);
    else
    	libusb_exit(0);
}

int acm_wait_for_device()
{
    struct libusb_device_descriptor             device_desc;
    struct libusb_config_descriptor            *config_desc;
    const struct libusb_interface_descriptor   *interface_desc;
    const struct libusb_endpoint_descriptor    *ep;
    libusb_device *fwld_device = NULL;
	libusb_device **devs;
    int i, j, k, cnt, ret, acm_found = 0;

    while(1)
    {
        cnt = libusb_get_device_list(ctx, &devs); //get the list of devices
    	for( i = 0; i < cnt; i++)
        {
            fwld_device = devs[i];
            (void)libusb_get_device_descriptor(fwld_device, &device_desc);

            if( device_desc.idVendor == ACM_VID &&
                device_desc.idProduct == ACM_PID)
            {
                acm_found = 1;
                break;
            }
        }
        if( acm_found)
            break;

        sleep(1);
    }
    printf("Device %04X:%04X found.\n", ACM_VID , ACM_PID);

    if( device_desc.bNumConfigurations > 1)
        goto exit_2;

    ret = libusb_get_config_descriptor(fwld_device, 0, &config_desc);
    for (i = 0; i < config_desc->bNumInterfaces; i++)
    {
        printf("\n\nInterface[%d]: num_altsetting=%d\n", i, config_desc->interface[i].num_altsetting);
        for(k = 0; k < config_desc->interface[i].num_altsetting; k++)
        {
            interface_desc = &config_desc->interface[i].altsetting[k];
            if (interface_desc->bInterfaceNumber == acm_intf)
            {
            	interface_no = interface_desc->bInterfaceNumber;
            }
            printf("Interface class: %02X ", interface_desc->bInterfaceClass);
            printf("Interface number: %02X ", interface_desc->bInterfaceNumber);

            for( j = 0; j < interface_desc->bNumEndpoints; j++)
            {
                printf("End_point[%d]: ", j);
                ep = &interface_desc->endpoint[j];
                if( !ep)
                {
                    printf("Could not get Endpoint iter!!\n");
                    goto exit_2;
                }

                if(( is_bulk_endpoint(ep->bmAttributes)) && (interface_desc->bInterfaceNumber == acm_intf))
                {
                    if (ep->bEndpointAddress & 0x80)
                    {
                        ep_in = ep->bEndpointAddress;
                        printf("ep_in=%x ", ep_in);
                    }
                    else
                    {
                        ep_out = ep->bEndpointAddress;
                        printf("ep_out=%x, max_size=%d ", ep_out, ep->wMaxPacketSize);
                        //max_out_size = ep->wMaxPacketSize;
                    }

                }
            }
        }
    }

exit_2:
    libusb_free_device_list(devs, 1);
    return 0;
}

int main(int argc, char *argv[])
{
	int len=0;
	char buf[0x10000];
    char int_buf[50];
    char * pch;
	char filename[1000], filename1[1000], orig_filename[1000];

    char filesize_threshold_str[1000];
    int  filesize_threshold=0;
    int  filesize_threshold_default=1000000000;
    int toggle_flag=0;
    
    int fd = -1;
    int total, counter =0;
    mode_t mode = S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH;

    memset(filename, '\0', sizeof(filename));
    
	if (argc < 3 )
	{
		fprintf(stderr, "Usage: %s {acm0|acm1|acm2} <filename.istp> [<max file size > <toggle (1= enable)>]\n", argv[0]);
		return 1;
	}

	if (!strcasecmp(argv[1], "acm0"))
	{
		acm_intf=1;
	}
	else if (!strcasecmp(argv[1], "acm1"))
	{
		acm_intf=3;
	}
	else if (!strcasecmp(argv[1], "acm2"))
	{
		acm_intf=5;
	}
	else
	{
		fprintf(stderr, "Unknown device %s\n", argv[1]);
		return 2;
	}
	acm_ctl_intf = acm_intf-1;

	strcpy(filename, argv[2]);
    pch=strstr(filename, ".istp");
    if(!pch)
    {
        fprintf(stderr, "Filename: %s does not have correct extension, now appending .istp\n", filename);
        strcat(filename, ".istp");
        fprintf(stderr, "New log file name: %s\n", filename);
        pch=strstr(filename, ".istp");
    }
   
    if (argc > 3 )
    {
        int my_rc=0;
        strcpy(filesize_threshold_str, argv[3]);
        fprintf(stderr, "Capture file size threshold val: %s \n", filesize_threshold_str);
        filesize_threshold = atoi(filesize_threshold_str);
        fprintf(stderr, "Capture file size threshold int val: %d \n", filesize_threshold);

        if (0 != filesize_threshold)
        {
            fprintf(stderr, "Capture file size threshold value used: %d \n", filesize_threshold);
        }
        else
        {
            filesize_threshold = filesize_threshold_default;
            fprintf(stderr, "Invalid capture file size threshold val specified - using default: %d (1GB)\n",
                filesize_threshold );
        }
    }
    
    // specify toggle between two files rather than continue
    // to write new output files indefinitely files
    if (argc > 4 )
    {
        fprintf(stderr, "Capture file toggle parm specified: %s \n", argv[4]);

    	if (!strcmp(argv[4], "1"))
        {
            fprintf(stderr, "Capture file toggle enabled \n");
            toggle_flag=1;
        }
        else
        {
            fprintf(stderr, "Capture file toggle parm unrecognised - toggle not enabled \n");
        }
        
    }
    
	fprintf(stderr, "Writing to trace file: %s...\n", filename);
	// handler to do a clean termination by breaking the loop
	signal(SIGINT, sig_handler);
	// handler to trigger a statistic print
	signal(SIGHUP, sig_handler);

	if ( 0 != acm_usb_init() )
	{
		fprintf(stderr, "USB init failed\n");
		goto error0;
	}
	acm_wait_for_device();
	if ( 0 != acm_usb_open() )
	{
		fprintf(stderr, "USB open failed\n");
		goto error1;
	}

	if ( 0 != acm_usb_setup() )
	{
		fprintf(stderr, "USB setup failed\n");
		goto error1;
	}
    
    
    memset(orig_filename, '\0', sizeof(orig_filename));
    /*extract the original filename.*/
    strncpy(orig_filename, filename, (pch-filename));
    fprintf(stderr, "Original file name: %s \n", orig_filename);

    // use numeric tag for all files form the first
    counter ++;
    sprintf(int_buf, "%02d",counter);
    memset(filename1, '\0', sizeof(filename1));
    snprintf(filename1, sizeof(filename1), "%s%s%s%s", orig_filename, "_", int_buf, ".istp");
    strncpy(filename, filename1, strlen(filename1));
    
	fd = open(filename, O_CREAT | O_RDWR | O_TRUNC, mode);
	if ( fd < 0 )
	{
		fprintf(stderr, "Could not open file: %s for output\n", filename);
		goto error1;
	}    
        
    while ( running )
    {
    	if (statistic)
    	{
    		fprintf(stderr, "Total recorded bytes %d\n", total);
    		statistic = 0;
    	}
		//acm_usb_write("AT\r",3);
		len = acm_usb_read(buf, sizeof(buf), 5000);
		if (len > 0 )
		{
			int pos = 0;
			int written = 0;
			while (pos < len)
			{
		       written = write(fd, buf+pos, len-pos);
		       if (written < 0)
		       {
		    	   perror("Writing output file - aborting\n");
		           goto error2;
		       }
		       else
		       {
		    	   total += written;
                   //fprintf(stderr, "@Total recorded bytes %d\n", total);
                   if(total >= filesize_threshold)                   
                   {            
                    fprintf(stderr, "Closing file %s\n", filename); 
                    if(close(fd))
                    {
                        fprintf(stderr, "Error closing file %s\n", filename);
                        goto error2;
                    }
                    total =0;
                    
                    if  (0 != toggle_flag)
                    {
                        if ( counter == 1)
                            counter=0;
                        else 
                            counter=1;
                    }
                    else
                    {
                        counter ++;
                    }
                    sprintf(int_buf, "%02d",counter);
                    memset(filename1, '\0', sizeof(filename1));
                    snprintf(filename1, sizeof(filename1), "%s%s%s%s", orig_filename, "_", int_buf, ".istp");
                    strncpy(filename, filename1, strlen(filename1));
                    fprintf(stderr, "Opening new log file: %s\n", filename); 
                    fd = open(filename, O_CREAT | O_RDWR | O_TRUNC, mode);
                    if ( fd < 0 )
                    {
                        fprintf(stderr, "Could not open file: %s for logging\n", filename);
                        goto error1;
                    }                    
                   }
                   pos += written;
		       }
			}
		}
	}
error2:
    close(fd);
error1:
	acm_usb_close();
error0:
	acm_usb_deinit();
}
