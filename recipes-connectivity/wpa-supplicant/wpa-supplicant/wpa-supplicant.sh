#!/bin/sh


WPA_SUP_BIN="/usr/sbin/wpa_supplicant"
WPA_SUP_PNAME="wpa_supplicant"
WPA_SUP_PIDFILE="/var/run/wpa_supplicant.$IFACE.pid"
WPA_COMMON_CTRL_IFACE="/var/run/wpa_supplicant"
WPA_SUP_OPTIONS="-B -P $WPA_SUP_PIDFILE -i $IFACE"
VERBOSITY=0


platform=$(strings /proc/device-tree/compatible | grep vivint | sed s/^vivint,//)

if [ -s "$IF_WPA_CONF" ]; then
	if [ -f /media/extra/conf/network/wpa_supplicant_wireless.conf ]; then
		IF_WPA_CONF="/media/extra/conf/network/wpa_supplicant_wireless.conf"
	fi

        WPA_SUP_CONF="-c $IF_WPA_CONF"
else
        exit 0
fi

if [ ! -x "$WPA_SUP_BIN" ]; then

        if [ "$VERBOSITY" = "1" ]; then
                echo "$WPA_SUP_PNAME: binaries not executable or missing from $WPA_SUP_BIN"
        fi

        exit 1
fi

if [ "$MODE" = "start" ] ; then
        # driver type of interface, defaults to wext when undefined
        if [ -s "/etc/wpa_supplicant/driver.$IFACE" ]; then
                IF_WPA_DRIVER=$(cat "/etc/wpa_supplicant/driver.$IFACE")
        elif [ -z "$IF_WPA_DRIVER" ]; then

                if [ "$VERBOSITY" = "1" ]; then
                        echo "$WPA_SUP_PNAME: wpa-driver not provided, using \"wext\""
                fi

                IF_WPA_DRIVER="wext"
        fi

        # if we have passed the criteria, start wpa_supplicant
        if [ -n "$WPA_SUP_CONF" ]; then
		if [ $platform'x' == 'nenex' ] || [ $platform'x' == 'gallusx' ] || [ $platform'x' == 'magellanx' ] || [ $platform'x' == 'yellowstonex' ] ; then
			IF_WPA_DRIVER_OPTION="-Dnl80211"
			WPA_P2P0_OPTIONS="-N -i p2p0 $IF_WPA_DRIVER_OPTION -c /etc/wpa_supplicant_p2p0.conf -puse_p2p_group_interface=1"
		else
			IF_WPA_DRIVER_OPTION="-D$IF_WPA_DRIVER"
			WPA_P2P0_OPTIONS=""
		fi


                if [ "$VERBOSITY" = "1" ]; then
                        echo "$WPA_SUP_PNAME: $WPA_SUP_BIN $WPA_SUP_OPTIONS $WPA_SUP_CONF $IF_WPA_DRIVER_OPTION"
                fi

                start-stop-daemon --start --quiet \
                        --name $WPA_SUP_PNAME --startas $WPA_SUP_BIN --pidfile $WPA_SUP_PIDFILE \
                        --  $WPA_SUP_OPTIONS $IF_WPA_DRIVER_OPTION $WPA_SUP_CONF $WPA_P2P0_OPTIONS
        fi

        # if the interface socket exists, then wpa_supplicant was invoked successfully
        if [ -S "$WPA_COMMON_CTRL_IFACE/$IFACE" ]; then

                if [ "$VERBOSITY" = "1" ]; then
                        echo "$WPA_SUP_PNAME: ctrl_interface socket located at $WPA_COMMON_CTRL_IFACE/$IFACE"
                fi

                exit 0

        fi

elif [ "$MODE" = "stop" ]; then

        if [ -f "$WPA_SUP_PIDFILE" ]; then

                if [ "$VERBOSITY" = "1" ]; then
                        echo "$WPA_SUP_PNAME: terminating $WPA_SUP_PNAME daemon"
                fi

                start-stop-daemon --stop --quiet \
                        --name $WPA_SUP_PNAME --pidfile $WPA_SUP_PIDFILE

                if [ -S "$WPA_COMMON_CTRL_IFACE/$IFACE" ]; then
                        rm -f $WPA_COMMON_CTRL_IFACE/$IFACE
                fi

                if [ -f "$WPA_SUP_PIDFILE" ]; then
                        rm -f $WPA_SUP_PIDFILE
                fi
        fi

fi

exit 0

