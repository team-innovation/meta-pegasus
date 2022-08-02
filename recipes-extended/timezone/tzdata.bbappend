DEFAULT_TIMEZONE = "US/Mountain"

pkg_postinst_ontarget_${PN}-core () {
#!/bin/sh -e
# Copy over old timezone to new run partition

    eval $(fw_printenv bootnum || echo bootnum=1)

    case $bootnum in
        1)
            previous_localtime=/media/mmcblk2p3/etc/localtime
            previous_timezone=/media/mmcblk2p3/etc/timezone
            ;;
        2)
            previous_localtime=/media/mmcblk2p2/etc/localtime
            previous_timezone=/media/mmcblk2p2/etc/timezone
            ;;
        *)
            exit 0
            ;;
    esac

    new_path="/etc"
    zone_location="/usr/share/zoneinfo"
    if [ -f $previous_timezone ]; then
        actual_zone=$(readlink $previous_localtime)
        cp -p $previous_timezone $new_path
        cd $new_path && ln -sf $actual_zone localtime
    else
        echo $DEFAULT_TIMEZONE > $new_path/timezone
        cd $new_path && ln -sf $zone_location/$DEFAULT_TIMEZONE localtime
    fi
}
