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
#include <signal.h>
#include <ctype.h>

#include <sys/fcntl.h>
#include <sys/ioctl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/time.h>

#define TIMEOUT 10

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

enum parsestate {
	PARSE_PRECSI = 0,
	PARSE_CSI,
	PARSE_LINES,
	PARSE_COLS,
	PARSE_DONE
};

static int ttyrestore(void)
{
	tcsetattr(ttyfd, TCSADRAIN, &saved_tio);
}

static void getsize_timeout(int sig)
{
	ttyrestore();
	exit(1);

}


static int getsize(int *pcols, int *plines)
{
	char *outstr = "\0337\033[r\033[999;999H\033[6n\0338";
	int len = strlen(outstr);
	unsigned char c;
	enum parsestate state;
	int cols, lines;
	struct itimerval it;

	signal(SIGALRM, getsize_timeout);
	memset((char *) &it, 0, sizeof(struct itimerval));
	it.it_value.tv_sec = TIMEOUT;
	setitimer(ITIMER_REAL, &it, (struct itimerval *) NULL);

	write(ttyfd, outstr, len);

	state = PARSE_PRECSI;
	cols = 0;
	lines = 0;
	do {
		read(ttyfd, &c, 1);
		switch (state) {
		case PARSE_PRECSI:
			if (c == '\033')
				state = PARSE_CSI;
			break;
		case PARSE_CSI:
			if (c == '[')
				state = PARSE_LINES;
			break;
		case PARSE_LINES:
			if (c == ';')
				state = PARSE_COLS;
			else if (isdigit(c))
				lines = lines * 10 + c - '0';

			break;
		case PARSE_COLS:
			if (c == 'R')
				state = PARSE_DONE;
			else if (isdigit(c))
				cols = cols * 10 + c - '0';
			break;
		case PARSE_DONE:
			fprintf(stdout, "should never be here\n");
			break;
		}
	} while (c != 'R');
	*pcols = cols;
	*plines = lines;

	memset((char *) &it, 0, sizeof(struct itimerval));
	setitimer(ITIMER_REAL, &it, (struct itimerval *) NULL);
	return 0;
}

static void setsize(int cols, int lines)
{
	struct winsize ws;

	if (ioctl(ttyfd, TIOCGWINSZ, &ws) != -1) {
		if (ws.ws_col != cols || ws.ws_row != lines) {
			memset(&ws, 0, sizeof ws);
			ws.ws_col = cols;
			ws.ws_row = lines;
			if (ioctl(ttyfd, TIOCSWINSZ, &ws) == -1) {
				fprintf(stderr, "TIOCSWINSZ failed %s\n",
						strerror(errno));
			}
		}
	}
}


int main(int argc, char **argv)
{
	int cols, lines;

	ttyfd = open("/dev/tty", O_RDWR);
	ttyraw();
	getsize(&cols, &lines);
	ttyrestore();
	setsize(cols, lines);
	printf("COLUMNS=%d;\n", cols);
	printf("LINES=%d;\n", lines);
	printf("export COLUMNS LINES;\n");
}
