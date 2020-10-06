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
    [ ! -d "$HOME"/.gnupg ] && mkdir -p "$HOME"/.gnupg
}

rm_roubaix_logs()
{
    if [ -e /media/extra/log/roubaix.log ] || [ -e /media/extra/log/roubaix.log*.gz ]
    then
        echo "Remove roubaix logs from /media/extra/log"
        rm /media/extra/log/roubaix*
    fi
}

update_start_sshd_count()
{
    count_file="/media/bootscript/start_sshd_count"
    if [ -f $count_file ] ; then
	count=$(cat $count_file)
	if [ $count -gt 0 ]; then
	    count=$(expr $count - 1)

	    echo "Set start_sshd_count count to $count"
	    mount -o remount,rw /media/bootscript 
            echo $count > $count_file
	    mount -o remount,ro /media/bootscript 

	    # bring eth0 up
	    ifup eth0

	    # start sshd
	    /etc/init.d/sshd force-start
	    /opt/2gig/utils/password_utils --fixroot
	fi
    fi
}

# check to make sure public key is in /media/extra/conf
check_pub_key

# remove roubaix logs if any are still around
rm_roubaix_logs

# set the system root password to a random value when touchlink starts
/opt/2gig/utils/password_utils --random


# Give touchscreen a quick reset to clear and re-initialize
if [ -e /sys/class/input/input0/device/reset ] ; then
	echo 1 > /sys/class/input/input0/device/reset

	# FRANKEN HUB use ethernet dongle so the ethernet is eth1
	if grep -q brazen /etc/hostname ; then	
		sed -i 's/"ethernet_iface": "eth0"/"ethernet_iface": "eth1"/' /opt/2gig/netd/conf_files/brazen/netd_conf.json
		sed -i 's/"wireless_iface": "apcli0"/"wireless_iface": "wlan0"/' /opt/2gig/netd/conf_files/brazen/netd_conf.json
		sed -i 's/ra0/wlan0/' /etc/udhcpd.conf
	fi
else
	# HUB PLUS mod
	# decrement start_sshd_count
	update_start_sshd_count

	# comment mosquitto bind address
	sed -i '/bind_address/ s/^#*/#/' /etc/mosquitto/mosquitto.conf

	# remove pumpernickel entry
	rm -f /etc/procman.d/pumpernickel
fi
