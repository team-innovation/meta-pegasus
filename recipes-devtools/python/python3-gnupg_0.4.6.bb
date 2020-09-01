SUMMARY = "Python interface to gnupg"
HOMEPAGE = "https://docs.red-dove.com/python-gnupg/"
SECTION = "devel/python"
LICENSE = "BSD-2-Clause"
SRCNAME = "python-gnupg"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b0b7ac63b60085b23fa9f7e1951daa1d"

SRC_URI[md5sum] = "1a25d1aa3a6a8b5c8ac307deab33a7d7"
SRC_URI[sha256sum] = "3aa0884b3bd414652c2385b9df39e7b87272c2eca1b8fcc3089bc9e58652019a"

PYPI_PACKAGE = "python-gnupg"
inherit setuptools3 pypi

RDEPENDS_${PN} = "gnupg"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
