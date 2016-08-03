#!/bin/bash
#
if [ -e /sys/class/tty/ttyGS0 ]; then
	/sbin/getty -L ttyGS0 115200 xterm
else
	sleep 100000
fi
