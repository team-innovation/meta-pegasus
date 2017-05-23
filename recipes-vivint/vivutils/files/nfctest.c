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

static const char WALL_SLY_PATH[] = "/dev/ttyUSB0";
static const char SLIMLINE_PATH[] = "/dev/ttymxc1";

static void ttyraw(void)
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
	/* Set Baud Rate */
	cfsetospeed (&tio, (speed_t)B57600);
	cfsetispeed (&tio, (speed_t)B57600);

	tcsetattr(ttyfd, TCSANOW, &tio);
}

const char *get_nfc_tty_path()
{
	if (access("/etc/wallsly-version", F_OK) != -1) {
		return WALL_SLY_PATH;
	} else if (access("/etc/slimline-version", F_OK) != -1) {
		return SLIMLINE_PATH;
	} else {
		return NULL;
	}
}

static void
usage (char *s)
{
	printf("Usage: %s <option>\n", s);
	printf("   -e: Echo only\n");
	printf("   -r: Read tag\n");
}

int main(int argc, char **argv)
{
	int i, echo_test, read_test, cols, lines, timeout;
	char reset = 0x01;
	char echo[] = "\x55";
	char prot_select[] = "\2\2\2\0";
	char send_recv[] = "\4\2&\7";
	char read_tag[] = "\4\3\x30\0(";
	const int RET_VALID = 0x80;
	const int BUFFER_LEN = 30;
	char recv_data[BUFFER_LEN];

	if(argc == 1 || argc > 2) {
		usage(argv[0]);
		return 1;
	}

	/* Check args */
	echo_test = !strcmp(argv[1], "-e");
	read_test = !strcmp(argv[1], "-r");
	if(!echo_test && !read_test) {
		usage(argv[0]);
		return 1;
	}

	/* Open nfc tty device */
	const char *tty_path = get_nfc_tty_path();
	if (! tty_path) {
		fprintf(stderr, "No tty device defined for this device. Goodbye.\n");
		return 1;
	}
	ttyfd = open(tty_path, O_RDWR | O_NOCTTY);
	if(ttyfd < 0) {
		fprintf(stderr, "Error! Cannot open %s\n", tty_path);
		return 1;
	}
	if (0 != lockf(ttyfd, F_TLOCK, 0)) {
		fprintf(stderr, "Error! Can't lock %s\n: %s. Does another process have it locked? Try running 'lslocks'.", tty_path, strerror(errno));
		return 1;
	}
	ttyraw();
	
	/* Echo */
	printf("NFC Echo: 0x%x\n", echo[0]);
	write(ttyfd, echo, 1);
	memset(recv_data, 0, BUFFER_LEN);
	read(ttyfd, recv_data, 2);
	printf("NFC Read: ");
	for(i=0; i<2; i++)
		printf("0x%x ", recv_data[i]);
	printf("\n");

	if(echo_test)
		return 0;

	/* Set protocol */
	printf("NFC Set Protocol: 0x%02x, 0x%02x, 0x%02x, 0x%02x\n", 
		prot_select[0], prot_select[1], prot_select[2], prot_select[3]);
	write(ttyfd, prot_select, 4);
	memset(recv_data, 0, BUFFER_LEN);
	read(ttyfd, recv_data, 2);
	printf("NFC Read: ");
	for(i=0; i<2; i++)
		printf("0x%x ", recv_data[i]);
	printf("\n");

	/* Send/Receive command */
	printf("NFC Send/Recv: 0x%02x, 0x%02x, 0x%02x, 0x%02x\n", 
		send_recv[0], send_recv[1], send_recv[2], send_recv[3]);
	write(ttyfd, send_recv, 4);
	memset(recv_data, 0, BUFFER_LEN);
	read(ttyfd, recv_data, 7);
	timeout = 0;
	while(recv_data[0] != RET_VALID && timeout++ < 5) {
		write(ttyfd, send_recv, 4);
		read(ttyfd, recv_data, 7);
	}
	printf("NFC Read: ");
	for(i=0; i<7; i++)
		printf("0x%x ", recv_data[i]);
	printf("\n");

	if(recv_data[0] != RET_VALID) {
		printf("Error! Cannot send/recv tag!\n");
		return 1;
	}

	/* Read Tag */
	printf("NFC Read Tag: 0x%02x, 0x%02x, 0x%02x, 0x%02x, 0x%02x\n", 
		read_tag[0], read_tag[1], read_tag[2], read_tag[3], read_tag[4]);
	write(ttyfd, read_tag, 5);
	memset(recv_data, 0, BUFFER_LEN);
	read(ttyfd, recv_data, 23);
	timeout = 0;
	while(recv_data[0] != RET_VALID && timeout++ < 5) {
		write(ttyfd, read_tag, 5);
		read(ttyfd, recv_data, 23);
	}
	printf("NFC Read: ");
	for(i=0; i<23; i++)
		printf("0x%x ", recv_data[i]);
	printf("\n");
	if(recv_data[0] != RET_VALID) {
		printf("Error! Cannot read tag!\n");
		return 1;
	}
	else {
		printf("PASS!\n");
		return 0;
	}
}
