#!/bin/sh

. /etc/init.d/functions

check_pub_key()
{
	if [ ! -e /media/extra/conf/vivintbuild_gpg.pub ]
	then
		echo "Copy public key to /media/extra/conf"
		cp -a /etc/vivintbuild_gpg.pub /media/extra/conf/vivintbuild_gpg.pub
	fi
}

# check to make sure public key is in /media/extra/conf
check_pub_key
# Give touchscreen a quick reset to clear and re-initialize
echo 1 > /sys/devices/soc0/soc.0/2100000.aips-bus/21a4000.i2c/i2c-1/1-004a/reset
