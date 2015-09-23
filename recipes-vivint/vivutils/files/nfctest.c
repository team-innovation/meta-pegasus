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

static void ttyraw_orig(void)
{
	struct termios tio;

	if (tcgetattr(ttyfd, &saved_tio) != 0) {
		fprintf(stderr, "tcgetattr failed: %s\n",
				strerror(errno));
		exit(1);
	}
	cfmakeraw(&tio);
	tio.c_iflag = ICRNL|IXANY;
	tio.c_oflag = OPOST|ONLCR;
	tio.c_cflag = CREAD|CS8|HUPCL;
	tio.c_cc[VMIN] = 1;
	tio.c_cc[VTIME] = 0;
	cfsetispeed(&tio, cfgetispeed(&saved_tio));
	cfsetospeed(&tio, cfgetospeed(&saved_tio));
	tcsetattr(ttyfd, TCSANOW, &tio);
}

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
	char c = 0x55;

	ttyfd = open("/dev/ttymxc1", O_RDWR | O_NOCTTY);
	ttyraw();
	
	printf("NFC write 0x%x\n", c);
	write(ttyfd, &c, 1);

	read(ttyfd, &c, 1);
	printf("NFC read 0x%x\n", c);
}
