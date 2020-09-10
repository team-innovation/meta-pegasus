#!/bin/sh
# Copyright (c) 2017, Vivint.
### BEGIN INIT INFO
# Provides:          earlymod
# Required-Start:
# Required-Stop:
# Default-Start:     S
# Default-Stop:
# Short-Description: Load modules that must be loaded before udev loads the rest
### END INIT INFO

start()
{
    test -d /proc/device-tree/iod_bus && modprobe viv_iod
}

stop() {
	return 0
}

case "$1" in
	start|restart|reload)
		start
		;;
	stop)
		stop
		;;
	*)
		echo "Usage: $0 {start|stop|restart}"
		exit 1
esac

exit $?
