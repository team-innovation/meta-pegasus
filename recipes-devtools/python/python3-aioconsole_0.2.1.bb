SUMMARY = "Async console framework"
DESCRIPTION = "Asynchronous console and interfaces for asyncio"
HOMEPAGE = "https://github.com/vxgmichel/aioconsole"
SECTION = "devel/python"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=1d26494164e98318558fe828e4505193"

SRC_URI[md5sum] = "cf0a22b980bfd30e0c72160de8c66cbb"
SRC_URI[sha256sum] = "f63d007c5c0fd5a30769af0a6b749307ea86001c4f217d3e5e9e248ccdfec1d0"

PYPI_PACKAGE = "aioconsole"
inherit setuptools3 pypi

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-asyncio \
"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
