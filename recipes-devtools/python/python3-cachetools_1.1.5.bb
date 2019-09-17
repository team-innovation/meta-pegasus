DESCRIPTION = "Extensible memoizing collections and decorators"
SECTION = "devel/python"
HOMEPAGE = "https://pypi.python.org/pypi/cachetools"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=96c958336b659af4dad628356c5e43b6"
SRCNAME = "cachetools"
PR = "r1"

DEPENDS += "python3 python3-native python3-setuptools-native"

SRC_URI = " \
	https://pypi.python.org/packages/source/c/${SRCNAME}/${SRCNAME}-${PV}.tar.gz \
"


S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir

SRC_URI[md5sum] = "7674e953ebe414f7ffc7696a4516f7fe"
SRC_URI[sha256sum] = "9810dd6afaec9e9eaae5ec33f2aa7117214a7a3f8427e70ab23939fe4d1bf279"

