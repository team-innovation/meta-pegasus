# an initrd boot indicates this is an mfg boot
# so repartition eMMC
#
grep -q root=/dev/ram0 /proc/cmdline && {
	EMMCDEV=/dev/mmcblk0
	SERVERIP=192.168.1.2
	echo "About to reformat $EMMCDEV ... crtl-c to abort"
	sleep 5
	/usr/bin/slimline-initemmc $EMMCDEV $SERVERIP
}

