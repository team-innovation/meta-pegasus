DESCRIPTION = "Python more itertools."
HOMEPAGE = "https://pypi.org/project/more-itertools"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3396ea30f9d21389d7857719816f83b5"

PR = "r1"
SRCNAME = "more-itertools-${PV}"

SRC_URI = "https://files.pythonhosted.org/packages/88/ff/6d485d7362f39880810278bdc906c13300db05485d9c65971dec1142da6a/more-itertools-4.3.0.tar.gz"

S = "${WORKDIR}/${SRCNAME}"

SRC_URI[md5sum] = "42157ef9b677bdf6d3609ed6eadcbd4a"
SRC_URI[sha256sum] = "c476b5d3a34e12d40130bc2f935028b5f636df8f372dc2c1c01dc19681b2039e"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
