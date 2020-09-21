SUMMARY = "Python USB1 wrapper"
HOMEPAGE = "https://github.com/vpelletier/python-libusb1"
SECTION = "devel/python"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING.LESSER;md5=4fbd65380cdd255951079008b364516c"

DEPENDS += "libusb1"

SRC_URI[md5sum] = "2aaadaec2f2ea4c31f9d0afd49a6b1af"
SRC_URI[sha256sum] = "240f65ac70ba3fab77749ec84a412e4e89624804cb80d6c9d394eef5af8878d6"

PYPI_PACKAGE = "libusb1"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

