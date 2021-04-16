SUMMERY = "Core utilities for Python packages"
DESCRIPTION = "Reusable core utilities for various Python Packaging interoperability specifications."
HOMEPAGE = "https://pypi.org/project/packaging/#description"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=faadaedca9251a90b205c9167578ce91"

SRC_URI[md5sum] = "5377308b3ba89f2d78c05e7f485be65d"
SRC_URI[sha256sum] = "5b327ac1320dc863dca72f4514ecc086f31186744b84a230374cc1fd776feae5"

PYPI_PACKAGE = "packaging"
inherit setuptools3 pypi

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-asyncio \
"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
