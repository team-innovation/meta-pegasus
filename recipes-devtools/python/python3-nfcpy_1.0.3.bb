SUMMARY = "Python NFC Library"
HOMEPAGE = "https://github.com/nfcpy/nfcpy"
SECTION = "devel/python"
LICENSE = "EUPL-1.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c547f12426ad5b4a3ac93658503ab096"

SRC_URI[md5sum] = "b3581b4538b979834dda602c3a0d6783"
SRC_URI[sha256sum] = "cf7d30e4a09f4dc4bb377b5a72ac607cf92a3e93dbcbb91cb90ec0343a95cdfe"

PYPI_PACKAGE = "nfcpy"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
