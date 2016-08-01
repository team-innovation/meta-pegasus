#!/bin/bash
#
if [ -e /dev/ttyGS0 ]; then
	/sbin/getty -L ttyGS0 115200 vt100
else
	sleep 100000
fi
