#!/bin/sh

. /etc/init.d/functions

check_pub_key()
{
	if [ ! -e /media/extra/conf/vivintbuild_gpg.pub ]
	then
		echo "Copy public key to /media/extra/conf"
		cp -a /usr/share/keys/vivintbuild_gpg.pub /media/extra/conf/vivintbuild_gpg.pub
	fi
}

# check to make sure public key is in /media/extra/conf
check_pub_key
# Give touchscreen a quick reset to clear and re-initialize
echo 1 > /sys/class/input/input0/device/reset
