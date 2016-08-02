#! /bin/sh
# Copyright (c) 2016, Vivint.
#
# Setup USB gadgets
# Call /usr/local/bin/gadgetsetup to:
#    setup serial and network gadgets 
#    see that file for details

gogogadget()
{
	/usr/local/bin/gadgetsetup
}

do_nothing() {
	return 0
}

case "$1" in
	start)
        if [ ! -e /media/extra/serial_lock ]; then
		    gogogadget
        fi
		;;
	stop|restart|reload)
		do_nothing
		;;
	*)
		echo "Usage: $0 {start|stop|restart}"
		exit 1
esac

exit $?
