DESCRIPTION = "Python JWT implementation"
HOMEPAGE = "https://pypi.org/project/PyJWT/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=68626705a7b513ca8d5f44a3e200ed0c"

PYPI_PACKAGE = "PyJWT"
SRC_URI[md5sum] = "a4712f980c008696e13e09504120b2a0"
SRC_URI[sha256sum] = "8d59a976fb773f3e6a39c85636357c4f0e242707394cadadd9814f5cbaa20e96"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit setuptools3 pypi
