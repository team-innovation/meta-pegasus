SUMMARY = "Timeout manager for asyncio"
DESCRIPTION = "Timeout context manager for asyncio programs"
SECTION = "devel/python"
HOMEPAGE = "https://github.com/python-trio/async_generator"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e7e2c6a927fea60b7e93d1c53ab6c43b"

PYPI_PACKAGE = "async_generator"
inherit setuptools3 pypi

SRC_URI[md5sum] = "078a29b4afb3d7f38c097a530f042a55"
SRC_URI[sha256sum] = "6ebb3d106c12920aaae42ccb6f787ef5eefdcdd166ea3d628fa8476abe712144"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-asyncio \
"
# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
