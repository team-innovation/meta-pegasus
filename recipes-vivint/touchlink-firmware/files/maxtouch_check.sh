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
	mxtusr=$(mxt-app -R -T 38)
        mxtpar=$(echo $mxtusr | xxd -p -r)
        if [ "$mxtfam" = '166' ]; then
                echo "Setting NVD panel"
                mxt-app --load /lib/firmware/maxtouch-wallsly_nvd.cfg
        elif [ "${mxtusr:0:2}" = "TA" ]; then
		echo "Setting Tianma panel"
		mxt-app --load /lib/firmware/maxtouch-wallsly_tianma.cfg 
	elif [ "${mxtusr:0:11}" = "01 15 06 04" ]; then
                echo "Setting Haier panel"
                mxt-app --load /lib/firmware/maxtouch-wallsly_haier.cfg
	else
		mxt-app --load /lib/firmware/maxtouch-wallsly_haier.cfg
		source /etc/profile.d/qt5.sh
		echo 0 > /sys/iodbus/lcd/lcd_power_off/value
		/usr/local/bin/touchcheck
		if [ "$?" -eq "1" ]; then
			echo "Setting Tianma panel"
			mxt-app --load /lib/firmware/maxtouch-wallsly_tianma.cfg
		else
			echo "Setting Haier panel"
			mxt-app --load /lib/firmware/maxtouch-wallsly_haier.cfg
		fi
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
