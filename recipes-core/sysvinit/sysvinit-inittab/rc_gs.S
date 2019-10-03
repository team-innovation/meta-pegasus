#!/bin/sh
#
if [ ! -e /media/extra/serial_lock ]; then
    if [ -e /sys/class/tty/ttyGS0 ]; then
	    /sbin/getty -L ttyGS0 115200 xterm
    else
        sleep 100000
    fi
else
	sleep 100000
fi
