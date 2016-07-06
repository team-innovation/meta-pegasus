#!/bin/sh
### BEGIN INIT INFO
# Provides: bootsplash
# Required-Start:
# Required-Stop:
# Default-Start:     S
# Default-Stop:
### END INIT INFO

. /etc/profile.d/qt5.sh

if [ ! -e /dev/fb0 ]; then
        return
fi

if [ -e /usr/local/bin/splash_app.py ]; then
    QT_EGLFS_IMX6_NO_FB_MULTI_BUFFER=1 /opt/2gig/utils/splash_app.py&
fi
