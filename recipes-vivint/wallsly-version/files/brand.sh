#!/bin/sh
# Copyright (c) 2017, Vivint.
### BEGIN INIT INFO
# Provides:          brand
# Required-Start:
# Required-Stop:
# Default-Start:     S
# Default-Stop:
# Short-Description: Set the unit type based on /proc/device-tree/compatible
### END INIT INFO

check_and_set()
{
	if grep -q wallsly /proc/device-tree/compatible; then
		if [ -e /etc/issue-orig ]; then
			printf "Already branded for WallSly"
			exit 0
		else
			printf "Branding for WallSly"

			mv /etc/issue /etc/issue-orig
			mv /etc/issue.net /etc/issue-orig.net
			cp /etc/issue_wallsly /etc/issue
			cp /etc/issue_wallsly.net /etc/issue.net

			mv /usr/bin/lsb_release /usr/bin/lsb_release-orig
			cp /usr/bin/lsb_release_wallsly /usr/bin/lsb_release
			chmod 755 /usr/bin/lsb_release

			rm -f /etc/init.d/multiplexerd
			rm -f /etc/procmand/multiplexerd
		fi
	fi
}

stop() {
	return 0
}

case "$1" in
	start|restart|reload)
		check_and_set
		;;
	stop)
		stop
		;;
	*)
		echo "Usage: $0 {start|stop|restart}"
		exit 1
esac

exit $?
