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
	dev="-d i2c-dev:2-004a"
	mxtfam=$(mxt-app $dev -i | grep Family | awk '{print $2}')
	mxtver=$(mxt-app $dev -i | grep Firmware | awk '{print $6}')
	mxtusr=$(mxt-app $dev -R -T 38)
	mxtpar=$(echo $mxtusr | xxd -p -r)
	mxtmfg="NA"
        if [ "${mxtusr:0:5}" = "0B 0E" ]; then
                echo "Setting BOE panel"
                mxt-app $dev --load /lib/firmware/maxtouch-wallsly_boe.cfg
		mxtmfg="BOE"
        elif [ "${mxtpar:0:2}" = "ND" ]; then
                echo "Setting NVD panel"
                mxt-app $dev --load /lib/firmware/maxtouch-wallsly_nvd.cfg
		mxtmfg="NVD"
        elif [ "${mxtpar:0:2}" = "TA" ]; then
		echo "Setting Tianma panel"
		mxt-app $dev --load /lib/firmware/maxtouch-wallsly_tianma.cfg 
		mxtmfg="Tianma"
	elif [ "${mxtusr:0:11}" = "01 15 06 04" ]; then
                echo "Setting Haier panel"
                mxt-app $dev --load /lib/firmware/maxtouch-wallsly_haier.cfg
		mxtmfg="Haier"
	elif [ "$mxtfam" = '166' ]; then
		if [ "$mxtver" = "V1.0.AB" ]; then
			echo "Setting NVD panel"
			mxt-app $dev --load /lib/firmware/maxtouch-wallsly_nvd.cfg
			mxtmfg="NVD"
		elif [ "$mxtver" = "V1.0.AA" ]; then
			echo "Setting BOE panel"
			mxt-app $dev --load /lib/firmware/maxtouch-wallsly_boe.cfg
			mxtmfg="BOE"
		fi
	else
		mxt-app --load /lib/firmware/maxtouch-wallsly_haier.cfg
		source /etc/profile.d/qt5.sh
		echo 0 > /sys/iodbus/lcd/lcd_power_off/value
		/usr/local/bin/touchcheck
		if [ "$?" -eq "1" ]; then
			echo "Setting Tianma panel"
			mxt-app $dev --load /lib/firmware/maxtouch-wallsly_tianma.cfg
		else
			echo "Setting Haier panel"
			mxt-app $dev --load /lib/firmware/maxtouch-wallsly_haier.cfg
		fi
	fi
    else
	dev="-d i2c-dev:1-004a"
	mxtmfg="Haier"
	mxtver=$(mxt-app $dev -i | grep Firmware | awk '{print $6}')
    fi

	boot_dir=/media/bootscript
	panelinfo_file=$boot_dir/panelinfo.txt
	mount -o remount,rw $boot_dir
	sed -i '/export CTP_/d' $panelinfo_file
	echo "export CTP_MODEL=$mxtmfg" >> $panelinfo_file
	echo "export CTP_VERSION=$mxtver" >> $panelinfo_file
    	mount -o remount,ro $boot_dir
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
