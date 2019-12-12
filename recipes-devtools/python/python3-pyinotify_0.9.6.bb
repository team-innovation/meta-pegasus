DESCRIPTION = "inotify for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=ab173cade7965b411528464589a08382"
SRCNAME = "pyinotify"
PR = "ml"

DEPENDS += "python3"
SRC_URI = " \
	https://pypi.python.org/packages/source/p/${SRCNAME}/${SRCNAME}-${PV}.tar.gz \
"


S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir

SRC_URI[md5sum] = "8e580fa1ff3971f94a6f81672b76c406"
SRC_URI[sha256sum] = "9c998a5d7606ca835065cdabc013ae6c66eb9ea76a00a1e3bc6e0cfe2b4f71f4"
