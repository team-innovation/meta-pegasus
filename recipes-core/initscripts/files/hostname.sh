#!/bin/sh
### BEGIN INIT INFO
# Provides:          hostname
# Required-Start:
# Required-Stop:
# Default-Start:     S
# Default-Stop:
# Short-Description: Set hostname based on /etc/hostname
### END INIT INFO
HOSTNAME=$(/bin/hostname)

# change hostname base on device
if grep -q wallsly /proc/device-tree/compatible; then
	if ! grep -q wallsly /etc/hostname; then
		echo "imx6dl-wallsly" > /etc/hostname
	fi
elif grep -q sly /proc/device-tree/compatible; then
	if ! grep -q skyhub /etc/hostname; then
        	echo "imx6dl-skyhub" > /etc/hostname
	fi
elif grep -q brazen /proc/device-tree/compatible; then
        if ! grep -q brazen /etc/hostname; then
                echo "imx6dl-brazen" > /etc/hostname
        fi

fi

hostname -b -F /etc/hostname 2> /dev/null
if [ $? -eq 0 ]; then
	exit
fi

# Busybox hostname doesn't support -b so we need implement it on our own
if [ -f /etc/hostname ];then
	hostname `cat /etc/hostname`
elif [ -z "$HOSTNAME" -o "$HOSTNAME" = "(none)" -o ! -z "`echo $HOSTNAME | sed -n '/^[0-9]*\.[0-9].*/p'`" ] ; then
	hostname localhost
fi
