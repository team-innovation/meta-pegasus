#!/bin/sh
# Copyright (c) 2017, Vivint.
### BEGIN INIT INFO
# Provides:          maxtouch_check
# Required-Start:
# Required-Stop:
# Default-Start:     S
# Default-Stop:
# Short-Description: Make sure the correct screen configuration is loaded 
### END INIT INFO

check_and_set() {
    if grep -q wallsly /proc/device-tree/compatible; then
        mxtfam=$(mxt-app -i | grep Family | awk '{print $2}')
        if [ "$mxtfam" = '166' ]; then
                echo "Setting NVD panel"
                cp /lib/firmware/maxtouch-wallsly_nvd.cfg /lib/firmware/maxtouch-wallsly.cfg
        else
                echo "Setting Haier panel"
                cp /lib/firmware/maxtouch-wallsly_haier.cfg /lib/firmware/maxtouch-wallsly.cfg
        fi
        mxt-app --load /lib/firmware/maxtouch-wallsly.cfg
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
