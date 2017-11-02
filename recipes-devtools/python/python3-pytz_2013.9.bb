DESCRIPTION = "pytz brings the Olson tz database into Python"
HOMEPAGE = "http://pytz.sourceforge.net"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=22b38951eb857cf285a4560a914b7cd6"
SRCNAME = "pytz"
PR = "r1"

SRC_URI = "https://pypi.python.org/packages/source/p/pytz/pytz-${PV}.tar.bz2 \
	   "

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools3

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

SRC_URI[md5sum] = "ec7076947a46a8a3cb33cbf2983a562c"
SRC_URI[sha256sum] = "4252226fa20e0715d4dcd05f9233f0076c44bca30e4f1793b2fe03cdfb422ed4"

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

