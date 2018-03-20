DESCRIPTION = "inotify for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=ab173cade7965b411528464589a08382"
SRCNAME = "pyinotify"
PR = "ml"

DEPENDS += "python3"
DEPENDS_virtclass-native += "python3-native"
SRC_URI = " \
	https://pypi.python.org/packages/source/p/${SRCNAME}/${SRCNAME}-${PV}.tar.gz \
"


S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir

SRC_URI[md5sum] = "b922aecb0ac532cfc51ab674e5f2e94c"
SRC_URI[sha256sum] = "447b01feaf25a2ad36e4101b583a8212bd701d5c34e330dd62dd9f32ba149aa6"

