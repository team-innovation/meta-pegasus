DESCRIPTION = "pytz brings the Olson tz database into Python"
HOMEPAGE = "http://pytz.sourceforge.net"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=22b38951eb857cf285a4560a914b7cd6"
SRCNAME = "pytz"
PR = "r1"

SRC_URI = "http://pypi.python.org/packages/source/p/pytz/pytz-${PV}.tar.bz2 \
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

SRC_URI[md5sum] = "ec7076947a46a8a3cb33cbf2983a562c"
SRC_URI[sha256sum] = "4252226fa20e0715d4dcd05f9233f0076c44bca30e4f1793b2fe03cdfb422ed4"

pkg_postinst_${PN}() {
#!/bin/sh
# create symlink to /usr/share/zoneinfo for pytz to use
#

if [ ! -h {D}/usr/lib/python3.3/site-packages/pytz ]
then
	rm -rf {D}/usr/lib/python3.3/site-packages/pytz/zoneinfo

	if [ -e {D}/usr/share/zoneinfo ]
	then
	    cd {D}/usr/lib/python3.3/site-packages/pytz ; ln -s {D}/usr/share/zoneinfo zoneinfo
	fi
fi
}

