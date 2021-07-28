#!/bin/sh
#
# Called from udev
#
# Attempt to mount any added block devices and umount any removed devices


MOUNT="/bin/mount"
PMOUNT="/usr/bin/pmount"
UMOUNT="/bin/umount"
name="`basename "$DEVNAME"`"

for line in `cat /etc/udev/mount.blacklist | grep -v ^#`
do
	if ( echo "$DEVNAME" | grep -q "$line" )
	then
		logger "udev/mount.sh" "[$DEVNAME] is blacklisted, ignoring"
		exit 0
	fi
done

mount_options() {
	for line in `cat /etc/udev/mount.options | grep -v ^#`
	do
		OPTIONS=""
		if ( echo "$line" | grep -q "$DEVNAME" )
		then
			OPTIONS="-o "`echo $line | awk -F= '{print $2}'`
			logger "udev/mount.sh" "[$DEVNAME] has options: "$OPTIONS
			break
		fi
	done
}

sendtopipe() {
	# Trap sigPipe(13), broken pipe or nothing connected.
	trap "echo No available process to accept data over pipe; exit" 13

	# Trap sighup(1), we get this when we succeed the write to the pipe
	trap "echo Success ; trap 13 ; exit" 1

	# Timeout
	TIMEOUT=0.3

	# Pipe
	PIPE=/tmp/.usbmounted

	# Mount Point to send over the pipe to our daemon
	MNTPNT=/media/$name

	# Check if the pipe exists
	if [ -p $PIPE ]
	then
		PID=$$
		sh -c "echo -n $MNTPNT $1 > $PIPE; kill -s 1 $PID" &
		BGPID=$!
		sleep $TIMEOUT

		# If we get here, we timed out, kill the bg process and remove the pipe.
		kill $BGPID
		echo Failed
		rm $PIPE
	else
		echo "Pipe does not exist"
		trap 1 13
	fi
}

automount() {
	! test -d "/media/$name" && mkdir -p "/media/$name"

	# CYM - removed the -o sync
	if ! $MOUNT -t auto $DEVNAME "/media/$name"
	then
		#logger "mount.sh/automount" "$MOUNT -t auto $DEVNAME \"/media/$name\" failed!"
		rm_dir "/media/$name"
	else
		logger "mount.sh/automount" "Auto-mount of [/media/$name] successful"
		touch "/tmp/.automount-$name"
		sendtopipe
	fi
}

rm_dir() {
	# We do not want to rm -r populated directories
	if test "`find "$1" | wc -l | tr -d " "`" -lt 2 -a -d "$1"
	then
		! test -z "$1" && rm -r "$1"
	else
		logger "mount.sh/automount" "Not removing non-empty directory [$1]"
	fi
}

if [ "$ACTION" = "add" ] && [ -n "$DEVNAME" ]; then
	mount_options
	if [ -x "$PMOUNT" ]; then
		$PMOUNT $OPTIONS $DEVNAME 2> /dev/null
	elif [ -x $MOUNT ]; then
    		$MOUNT $OPTIONS $DEVNAME 2> /dev/null
	fi

	# If the device isn't mounted at this point, it isn't configured in fstab
	# 20061107: Small correction: The rootfs partition may be called just "rootfs" and not by
	# 	    its true device name so this would break. If the rootfs is mounted on two places
	#	    during boot, it confuses the heck out of fsck. So Im auto-adding the root-partition
	#	    to /etc/udev/mount.blacklist via postinst 

	cat /proc/mounts | awk '{print $1}' | grep -q "^$DEVNAME$" || automount 
fi

if [ "$ACTION" = "remove" ] && [ -x "$UMOUNT" ] && [ -n "$DEVNAME" ]; then
	for mnt in `cat /proc/mounts | grep "$DEVNAME" | cut -f 2 -d " " `
	do
		$UMOUNT -l $mnt
	done

	# Remove empty directories from auto-mounter
	test -e "/tmp/.automount-$name" && rm_dir "/media/$name"

	# Send to pipe we ejected
	sendtopipe "eject"
fi
