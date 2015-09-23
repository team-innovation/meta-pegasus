/*
 * Copyright (c) 2015 Vivint
 *
 * Permission to use, copy, modify, and distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF MIND, USE, DATA OR PROFITS, WHETHER
 * IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING
 * OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <termios.h>
#include <unistd.h>

#include <sys/fcntl.h>
#include <sys/ioctl.h>
#include <sys/stat.h>
#include <sys/types.h>

static struct termios saved_tio;
static int ttyfd;

static void ttyraw(void)
{
	struct termios tio;

	if (tcgetattr(ttyfd, &saved_tio) != 0) {
		fprintf(stderr, "tcgetattr failed: %s\n",
				strerror(errno));
		exit(1);
	}

	/* Set Baud Rate */
	cfsetospeed (&tio, (speed_t)B57600);
	cfsetispeed (&tio, (speed_t)B57600);

	/* Setting other Port Stuff */
	tio.c_cflag     &=  ~PARENB;            // Make 8n1
	tio.c_cflag     &=  ~CSTOPB;
	tio.c_cflag     &=  ~CSIZE;
	tio.c_cflag     |=  CS8;

	tio.c_cflag     &=  ~CRTSCTS;           // no flow control
	tio.c_cc[VMIN]   =  1;                  // read doesn't block
	tio.c_cc[VTIME]  =  5;                  // 0.5 seconds read timeout
	tio.c_cflag     |=  CREAD | CLOCAL;     // turn on READ & ignore ctrl lines

	/* Make raw */
	cfmakeraw(&tio);
	tcsetattr(ttyfd, TCSANOW, &tio);
}

main(int argc, char **argv)
{
	int cols, lines;
	char c = 0;
	char str0[] = "\x55";
	char str1[] = "\2\2\2\0";
	char str2[] = "\4\2&\7";
	char str3[] = "\4\3\x30\0(";

	ttyfd = open("/dev/ttymxc1", O_RDWR | O_NOCTTY);
	ttyraw();
	
	printf("NFC write 0x%x\n", str0[0]);
	write(ttyfd, str0, 1);

	read(ttyfd, &c, 1);
	printf("NFC read 0x%x\n", c);

	printf("NFC write 0x%02x, 0x%02x, 0x%02x, 0x%02x\n", 
		str1[0], str1[1], str1[2], str1[3]);
	write(ttyfd, str1, 4);

	read(ttyfd, &c, 1);
	printf("NFC read 0x%x\n", c);

	printf("NFC write 0x%02x, 0x%02x, 0x%02x, 0x%02x\n", 
		str2[0], str2[1], str2[2], str2[3]);
	write(ttyfd, str2, 4);

	read(ttyfd, &c, 1);
	printf("NFC read 0x%x\n", c);

	printf("NFC write 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x\n", 
		str3[0], str3[1], str3[2], str3[3], str3[4]);
	write(ttyfd, str3, 4);

	read(ttyfd, &c, 1);
	printf("NFC read 0x%x\n", c);

	close(ttyfd);
}
