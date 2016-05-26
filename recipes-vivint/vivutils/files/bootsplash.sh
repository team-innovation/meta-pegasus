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

# Pull from /media/extra first for configuration complete bootsplash
# If it isn't there then use the default one in /usr/lib/images 
if [ -e /media/extra/lib/images/bootsplash.rgba ]; then
        dd if=/media/extra/lib/images/bootsplash.rgba of=/dev/fb0
elif [ -e /usr/lib/images/bootsplash.rgba ]; then
        dd if=/usr/lib/images/bootsplash.rgba of=/dev/fb0
fi
