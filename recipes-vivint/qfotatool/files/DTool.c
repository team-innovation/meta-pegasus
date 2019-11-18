#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>
#include <termios.h>
#include <sys/select.h>
#include <time.h>
#define READ_OK        (-1)
#define READ_ERROR     (-2)
#define READ_NOTHING   (-3)
#define READ_EXCEPT    (-4)
#define READ_CONNECT   (-5)
#define READ_END       (-6)

int pOpenPort(const char * port)
{
	struct termios newtio;

	int lFd = open(port, O_RDWR | O_NDELAY);
	if (lFd != -1) {
		/* read operations are set to blocking according to the VTIME value */
		fcntl(lFd, F_SETFL, 0);

		tcgetattr(lFd, &newtio);

		/* Set to 115200 */
		cfsetispeed(&newtio, B115200);
		cfsetospeed(&newtio, B115200);
		/*set char bit size*/
		newtio.c_cflag &= ~CSIZE;
		newtio.c_cflag |= CS8;
		newtio.c_cflag &= ~CSTOPB;
		newtio.c_cflag |= CLOCAL | CREAD;
		newtio.c_cflag &= ~(PARENB | PARODD);

		newtio.c_iflag &=~(INPCK | BRKINT | PARMRK | ISTRIP | INLCR | IGNCR | ICRNL);
		newtio.c_iflag |= IGNBRK;
		newtio.c_iflag &= ~(IXON | IXOFF | IXANY);

		newtio.c_lflag = 0;
		newtio.c_oflag = 0;
		/*set wait time*/
		newtio.c_cc[VMIN] = 0;
		newtio.c_cc[VTIME] = 20;

		tcsetattr(lFd, TCSANOW, &newtio);

		return(lFd);
	} /* endif */

	return(-1);
}

int pGetErrorCode(char * pBuffer)
{
	int lErrorCode = -1;

	char * lPtr = strstr(pBuffer, "+CME ERROR");

	if (lPtr != (char *)NULL) {
		lPtr += strlen("+CME ERROR:");
		lErrorCode = atoi(lPtr);
	} /* endif */

	return(lErrorCode);
}

int pGetResultCode(char *pBuffer)
{
	int lResultCode = -1;

	char *lPtr = strstr(pBuffer, "+QIND: \"FOTA\",\"END\",");
	if (lPtr != (char *)NULL) {
		lPtr += strlen("+QIND: \"FOTA\",\"END\",");
		lResultCode = atoi(lPtr);
	}

	return(lResultCode);
}

int pSendCommand(int fd, char * command)
{
	char cmdBuffer[255];
	int lCmdLen = strlen(command);
	int lRes;

	strcpy(cmdBuffer, command); strcat(cmdBuffer, "\r");
	lCmdLen ++;

	lRes = write(fd, cmdBuffer, lCmdLen);
	if (lRes != lCmdLen) {
		return(-1);
	} else {
		return(0);
	}
}

int pReadLine(int fd, char * buffer, int pBufLen)
{
	char * lPtr;
	int lRead;
	int lRes;

	fd_set lReadFds, lExceptFds;
	struct timeval lTimeout;

	FD_ZERO(&lReadFds); FD_ZERO(&lExceptFds);
	FD_SET(fd, &lReadFds); FD_SET(fd, &lExceptFds);
	lTimeout.tv_sec = 1;
	lTimeout.tv_usec = 0;

	lRes = select(1+fd, &lReadFds, NULL, &lExceptFds, &lTimeout);
	if (lRes == 0) {
		return(READ_NOTHING);
	} /* endif */

	if ((lRes < 0) || (FD_ISSET(fd, &lExceptFds))) {
		return(READ_EXCEPT);
	} /* endif */

	/* read characters into our string buffer until we get a CR or NL */
	lPtr = buffer;
	while ((lRead = read(fd, lPtr, buffer + pBufLen - lPtr - 1)) > 0) {
		lPtr += lRead;
		if (lPtr[-1] == '\n' || lPtr[-1] == '\r')
			break;
	} /* endwhile */

	if (lRead < 0) {
		return(READ_EXCEPT);
	} /* endif */

	/* null terminate the string and see if we got an OK response */
	*lPtr = '\0';

	if (strstr(buffer, "\nOK") != (char *)NULL) {
		return (READ_OK);
	} /* endif */

	if(strstr(buffer,"CONNECT")!=(char *)NULL)
	{
		return (READ_CONNECT);
	}

	if(strstr(buffer,"END")!=(char*)NULL)
	{
		return (READ_END);
	}

	if ((strstr(buffer, "\nERROR") != (char *)NULL) ||
	    (strstr(buffer, "\n+CME ERROR") != (char *)NULL)) {
		return (READ_ERROR);
	} /* endif */

	lRead = strlen(buffer);
	if (lRead > 0) {
		return(lRead);
	} /* endif */

	return(READ_NOTHING);
}

int pReadReply(int fd, char * buffer, int pBufLen)
{
	int lRes = -1;
	int lCount = 0;
	int lErrorCode;
	int lResultCode = 0;
	size_t i;

	do {
		lRes = pReadLine(fd, buffer, pBufLen);
		if ((lRes != READ_NOTHING) && (lRes != READ_EXCEPT)) {
			size_t buffer_length = strlen(buffer);
			for (i=0; i < buffer_length; i++) {
				if ((buffer[i] == '\r') || (buffer[i] == '\n')) {
					buffer[i] = ' ';
				} /* endif */
			} /* endfor */
			printf("< %d - [%d] - '%s'\n", lCount, lRes, buffer);
			if (lRes == READ_ERROR) {
				lErrorCode = pGetErrorCode(buffer);
				printf("<            Error code: %d\n", lErrorCode);
			}
			else if(lRes == READ_END)
			{
				lResultCode = pGetResultCode(buffer);
				if (lResultCode == 0)
					printf("Fota update succesful!!!\n");
				else {
					printf("Fota update error: %d\n", lResultCode);
					return(lResultCode);
				}
			} /* endif */
		} /* endif */
		lCount ++;
	} while ((lRes != READ_OK) && (lRes != READ_ERROR) && (lRes != READ_EXCEPT)&&(lRes!=READ_CONNECT)&&(lRes!=READ_END));

	return(0);
}

void pClosePort(int fd)
{
	close(fd);
}

void qsleep(int millsec)
{
	int second = millsec / 1000;
	if(millsec % 1000)
	second += 1;
	sleep(second);
}

int main(int argc,char *argv[])
{
	int opt,gFd,n,j,result;
	int iRet = 0;
	char sFile[256];
	char sPort[256];
	char sCommand[128];
	char sBuffer[256];
	char c[4 * 1024];
	size_t temp,progress,size,iRe,nFileSize;
	size_t progress_step = 10;

	bzero(sFile,sizeof(sFile));
	bzero(sPort,sizeof(sPort));
	bzero(sCommand,sizeof(sCommand));
	bzero(sBuffer,sizeof(sBuffer));
	while((opt=getopt(argc,argv,"p:f:"))!=-1)
	{
		switch(opt)
		{
			case 'f':
				if(access(optarg,0) != 0)
				{
					printf("ERROR:File not found\n");
					return 1;
				}
				memcpy(sFile,optarg,strlen(optarg));
				break;
			case 'p':
				if(access(optarg,0) != 0)
				{
					printf("ERROR:Port not found\n");
					return 1;
				}
				memcpy(sPort,optarg,strlen(optarg));
				break;
		}
	}
	if(sFile[0]=='\0')
	{
		printf("ERROR:Missing file parameter\n");
		return 1;
	}
	if(sPort[0]=='\0')
	{
		printf("ERROR:Missing port parameter\n");
		return 1;
	}
	/* get file size */
	FILE* pFile = fopen(sFile,"rb");
	if(pFile==NULL)
	{
		printf("ERROR:Open file failed\n");
		return 1;
	}
	fseek(pFile,0,SEEK_END);
	size = ftell(pFile);
	fseek(pFile,0,SEEK_SET);

	printf("Starting Quectel FOTA update with input file size: %zu bytes...\n", size);
	/* Sleep for 10 seconds to allow modem to stabilize */
	sleep(10);

	/* open port */
	gFd=pOpenPort(sPort);
	if(gFd==-1)
	{
		printf("ERROR:Open port failed\n");
		return 1;
	}

	printf("Deleting any existing update directory...\n");
	sprintf(sCommand,"AT+QFDEL=\"Update\"");
	pSendCommand(gFd,sCommand);
	bzero(sCommand,sizeof(sCommand));
	pReadReply(gFd,sBuffer,sizeof(sBuffer));

	printf("Uploading file to modem...\n");
	sprintf(sCommand,"AT+QFUPL=\"UFS:Update\",%lu", size);
	pSendCommand(gFd,sCommand);
	nFileSize = 0;
	if(pReadReply(gFd,sBuffer,sizeof(sBuffer))==0)
	{
		while(!feof(pFile))
		{
			if(nFileSize >= size)
			break;
			iRe = fread(&c, 1, sizeof(c), pFile);
			nFileSize += iRe;
			if (nFileSize > size) {
				iRe -= (nFileSize - size);
			}
			write(gFd, c, iRe);
			temp = (nFileSize * 100);
			progress = temp / size;
			if(progress == 100)
			{
				printf( "upload progress : %zu%% finished (%zu bytes)\n", progress, nFileSize);
			}
			else if (progress >= progress_step)
			{
				printf( "upload progress : %zu%% finished (%zu bytes)\n", progress, nFileSize);
				progress_step += 10;
			}
		}
		usleep(10000);
	}

	pReadReply(gFd,sBuffer,sizeof(sBuffer));
	if(strstr(sBuffer, "ERROR:") != NULL) {
		printf("Error uploading update.  Exiting.\n");
		return 1;
	}

	printf("Starting update...\n");
	bzero(sCommand,sizeof(sCommand));
	sprintf(sCommand,"at+qfotadl=\"/data/ufs/Update\"");
	pSendCommand(gFd,sCommand);
	pReadReply(gFd,sBuffer,sizeof(sBuffer));
	pClosePort(gFd);

	printf("Waiting for port to close...\n");
	for(n = 0; n < 60; n++)
	{
		if(access(sPort,0)!=0)
			break;
		sleep(1);
	}
	sleep(10);

	printf("Opening port to check update progress...\n");
	gFd=pOpenPort(sPort);
	if(gFd==-1)
	{
		printf("ERROR:Open %s failed\n", sPort);
		return 1;
	}

	/* check for update success */
	result = pReadReply(gFd,sBuffer,sizeof(sBuffer));
	if(result != 0)
	{
		switch (result) {
			case 502:
				printf("Error: The upgrade process exited for with unknown errors\n");
				break;
			case 504:
			case 505:
				printf("Error: Something is wrong with the upgrade package file\n");
				break;
			case 510:
				printf("Error: The patch does not match the source file in the module\n");
				break;
			case 511:
				printf("Error: The file system doesn\'t have enough space for upgrading\n");
				break;
			default:
				printf("Error: Firmware upgrading failed\n");
				break;
		}

		/* update failed */
		iRet = result;
	}
	pClosePort(gFd);

	if (iRet != 0) {
		return iRet;
	}

	sleep(15);

	printf("Waiting for port after status check...\n");
	for(n = 0; n < 60; n++)
	{
		if(access(sPort,F_OK)==0)
			break;
		sleep(1);
	}

	printf("Opening port to check final status...\n");
	gFd=pOpenPort(sPort);
	bzero(sCommand,sizeof(sCommand));
	sprintf(sCommand,"at+qgmr");
	pSendCommand(gFd,sCommand);
	if(gFd!=-1) pReadReply(gFd,sBuffer,sizeof(sBuffer));

	/* Clear the UFS folder */
	printf("Cleaning up after update...\n");
	sprintf(sCommand,"AT+QFDEL=\"Update\"");
	pSendCommand(gFd,sCommand);
	bzero(sCommand,sizeof(sCommand));
	pReadReply(gFd,sBuffer,sizeof(sBuffer));
	fclose(pFile);

	printf("Firmware update completed\n");
	return 0;
}
