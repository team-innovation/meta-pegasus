#!/bin/bash

THISFILE=$0
STATEFILE=/media/extra/conf/fake-hwclock

loadclock() {
    if [ ! -e "$STATEFILE" ]; then
        touch "$STATEFILE"
    fi

    local savedtime
    savedtime=$(stat -c %Y "$STATEFILE")
    if [ "$(date +%s)" -lt "$savedtime" ]; then
        echo "Restoring saved system time"
        date -s @"$savedtime"
        hwclock -u -w
    else
        echo "Not restoring old system time"
    fi
}

saveclock() {
	echo "Saving current time."
	touch "$STATEFILE"
}

case "$1" in
	load)
		loadclock
		;;
	set)
		echo "'set' is deprecated, use 'load' instead."
		echo "Consider using the systemd timer unit fake-hwclock-save.timer"
		loadclock
		;;
	save)
		saveclock
		;;
	*)
		echo "Usage: $THISFILE {load|save}"
		exit 1
		;;
esac
