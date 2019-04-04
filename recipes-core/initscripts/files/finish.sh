#!/bin/sh

. /etc/init.d/functions

check_pub_key()
{
	if [ ! -e /media/extra/conf/vivintbuild_gpg.pub ]
	then
		echo "Copy public key to /media/extra/conf"
		cp -a /usr/share/keys/vivintbuild_gpg.pub /media/extra/conf/vivintbuild_gpg.pub
	fi
	
	if [ ! -e /media/extra/conf/ca_gpg_1.pub ]; then
		echo "Copy public key to /media/extra/conf"
		cp -a /usr/share/keys/ca_gpg_1.pub /media/extra/conf/ca_gpg_1.pub
	fi	

	if [ ! -e /media/extra/conf/ca_gpg_2.pub ]; then
		echo "Copy public key to /media/extra/conf"
		cp -a /usr/share/keys/ca_gpg_2.pub /media/extra/conf/ca_gpg_2.pub
	fi

    if [ -e /media/extra/conf/vivintbuild_gpg.pub ]
    then
        validate_public_key
    else
        echo "Failed to install public key"
    fi
}

validate_public_key()
{
    count=0
    while [ $(md5sum /media/extra/conf/vivintbuild_gpg.pub | cut -c1-32) != 2e6e71bc978bdb35234a3e165842b2c5 ]
    do
        if [ $count -gt 4 ]
        then
            echo "Tried to install public key five times with no luck, giving up..."
            echo "Public key checksum should be 2e6e71bc978bdb35234a3e165842b2c5"
            break
        fi
        echo "Public key checksum MISMATCH"
        echo "Overwrite corrupt public key at /media/extra/conf"
        cp -a /usr/share/keys/vivintbuild_gpg.pub /media/extra/conf/vivintbuild_gpg.pub
        count=$((count+1))
        sleep 1
    done

    if [ $(md5sum /media/extra/conf/vivintbuild_gpg.pub | cut -c1-32) == 2e6e71bc978bdb35234a3e165842b2c5 ]
    then
        echo "Public key checksum matches"
    fi

    echo "Public key checksum is" $(md5sum /media/extra/conf/vivintbuild_gpg.pub | cut -c1-32)
}

rm_roubaix_logs()
{
    if [ -e /media/extra/log/roubaix.log ] || [ -e /media/extra/log/roubaix.log*.gz ]
    then
        echo "Remove roubaix logs from /media/extra/log"
        rm /media/extra/log/roubaix*
    fi
}

extract_nm_pkgs()
{
    if [ -f /srv/www/network/nm_pkgs.tar.gz ]
    then
       echo "Extracting Network Module packages"
       mkdir -p /srv/www/network/packages
       tar xzf /srv/www/network/nm_pkgs.tar.gz -C /srv/www/network/packages
    fi
}

# check to make sure public key is in /media/extra/conf
check_pub_key

# remove roubaix logs if any are still around
rm_roubaix_logs

# prepare NM packages for use
extract_nm_pkgs

# Give touchscreen a quick reset to clear and re-initialize
echo 1 > /sys/class/input/input0/device/reset
