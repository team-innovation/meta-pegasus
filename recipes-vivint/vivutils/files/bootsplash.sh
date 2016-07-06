#!/bin/sh
### BEGIN INIT INFO
# Provides: bootsplash
# Required-Start:
# Required-Stop:
# Default-Start:     S
# Default-Stop:
### END INIT INFO

if [ ! -e /dev/fb0 ]; then
        return
fi

if [ -e /usr/local/bin/splash_app.py ]; then
    /usr/local/bin/splash_app.py&
fi
