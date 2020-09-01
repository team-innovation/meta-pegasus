SUMMARY = "Python wrapper for libsodium"
HOMEPAGE = "https://github.com/stef/pysodium"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=63455d5536b401db53d4973c9d6399ea"

SRC_URI[md5sum] = "cbf77a418d14905f7b26687019cd222e"
SRC_URI[sha256sum] = "d70810792cc945a76bd5aa8726a49df272168acc814eb39613e7c29c47df8c6e"

PYPI_PACKAGE = "pysodium"
inherit setuptools3 pypi

RDEPENDS_${PN} = "libsodium"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
