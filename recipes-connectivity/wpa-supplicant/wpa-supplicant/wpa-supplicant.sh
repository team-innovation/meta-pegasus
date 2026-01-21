#!/bin/sh

WPA_SUP_BIN="/usr/sbin/wpa_supplicant"
WPA_SUP_PNAME="wpa_supplicant"
WPA_SUP_PIDFILE="/var/run/wpa_supplicant.$IFACE.pid"
WPA_COMMON_CTRL_IFACE="/var/run/wpa_supplicant"
WPA_SUP_OPTIONS="-B -P $WPA_SUP_PIDFILE -i $IFACE"
VERBOSITY="${VERBOSITY:-0}"

VIVINT_WPA_CONF='/media/extra/conf/network/wpa_supplicant_wireless.conf'

if [ -e "$VIVINT_WPA_CONF" ]; then
    WPA_SUP_CONF="-c $VIVINT_WPA_CONF"
elif [ -s "$IF_WPA_CONF" ]; then
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

if [ "$MODE" = "start" ]; then
    if [ "$VERBOSITY" = "1" ]; then
        echo "$WPA_SUP_PNAME: $WPA_SUP_BIN $WPA_SUP_OPTIONS $WPA_SUP_CONF -Dnl80211"
    fi

    pkill -9 -f "wpa_supplicant.*-i $IFACE"
    rm -f "$WPA_COMMON_CTRL_IFACE/$IFACE"
    rm -f "$WPA_SUP_PIDFILE"

    start-stop-daemon --start --quiet \
        --name $WPA_SUP_PNAME --startas $WPA_SUP_BIN --pidfile $WPA_SUP_PIDFILE \
        -- $WPA_SUP_OPTIONS -Dnl80211 $WPA_SUP_CONF

    if [ "$VERBOSITY" = "1" ] && [ -S "$WPA_COMMON_CTRL_IFACE/$IFACE" ]; then
        echo "$WPA_SUP_PNAME: ctrl_interface socket located at $WPA_COMMON_CTRL_IFACE/$IFACE"
    fi

elif [ "$MODE" = "stop" ]; then
    if [ -f "$WPA_SUP_PIDFILE" ]; then
        if [ "$VERBOSITY" = "1" ]; then
            echo "$WPA_SUP_PNAME: terminating $WPA_SUP_PNAME daemon"
        fi

        start-stop-daemon --stop --quiet \
            --name $WPA_SUP_PNAME --pidfile $WPA_SUP_PIDFILE

        rm -f "$WPA_COMMON_CTRL_IFACE/$IFACE"
        rm -f "$WPA_SUP_PIDFILE"
    fi
fi

exit 0
