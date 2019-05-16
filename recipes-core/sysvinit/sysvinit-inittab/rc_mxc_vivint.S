#!/bin/sh
#
if [ ! -e /media/extra/serial_lock ]; then
    /opt/2gig/utils/password_utils --fixroot
    if grep -sq ttymxc0 /proc/cmdline; then
        /bin/start_getty -L ttymxc0 115200 xterm
    elif grep -sq ttymxc1 /proc/cmdline; then
        /bin/start_getty -L ttymxc1 115200 xterm
    elif grep -sq ttymxc2 /proc/cmdline; then
        /bin/start_getty -L ttymxc2 115200 xterm
    elif grep -sq ttymxc3 /proc/cmdline; then
        /bin/start_getty -L ttymxc3 115200 xterm
    else
        sleep 100000
    fi
else
    sleep 100000
fi
