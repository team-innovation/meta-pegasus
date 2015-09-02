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

