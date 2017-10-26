DESCRIPTION = "pytz brings the Olson tz database into Python"
HOMEPAGE = "http://pytz.sourceforge.net"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=39ea92752a35cf67d8a885d8e3af3c69"
SRCNAME = "pytz"
PR = "r1"

SRC_URI = "http://pypi.python.org/packages/p/pytz/pytz-${PV}.zip \
           file://pytz_setup.patch"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils3

RDEPENDS_${PN} = "\
  python3-core \
  python3-datetime-tz \
"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

DEPENDS_${PN} = "\
  python3-distutils \
"

SRC_URI[md5sum] = "f89bde8a811c8a1a5bac17eaaa94383c"
SRC_URI[sha256sum] = "f5c056e8f62d45ba8215e5cb8f50dfccb198b4b9fbea8500674f3443e4689589"

pkg_postinst_${PN}() {
#!/bin/sh -e
# create symlink to /usr/share/zoneinfo for pytz to use
#

if [ x"$D" = "x" ]; then

    if [ ! -h /usr/lib/python3.3/site-packages/pytz ]; then
        rm -rf /usr/lib/python3.3/site-packages/pytz/zoneinfo

        if [ -e /usr/share/zoneinfo ]; then
            cd /usr/lib/python3.3/site-packages/pytz ; ln -s /usr/share/zoneinfo zoneinfo
        fi
    fi

# Copy over old timezone to new run partition

    eval $(fw_printenv bootnum || echo bootnum=1)

    case $bootnum in
        1)
            previous_localtime=/media/mmcblk0p6/etc/localtime
            previous_timezone=/media/mmcblk0p6/etc/timezone
            ;;
        2)
            previous_localtime=/media/mmcblk0p5/etc/localtime
            previous_timezone=/media/mmcblk0p5/etc/timezone
            ;;
        *)
            exit 0
            ;;
    esac

    new_path="/etc"
    default_timezone="US/Mountain"
    zone_location="/usr/share/zoneinfo"
    if [ -f $previous_timezone ]; then
        actual_zone=$(readlink $previous_localtime)
        cp -p $previous_timezone $new_path
        cd $new_path && ln -sf $actual_zone localtime
    else
        echo $default_timezone > $new_path/timezone
        cd $new_path && ln -sf $zone_location/$default_timezone localtime
    fi

else
    exit 1
fi
}

