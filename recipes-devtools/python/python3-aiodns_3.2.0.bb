SUMMARY = "Asynchronous console and interfaces for asyncio"
HOMEPAGE = "https://github.com/saghul/aiodns"
SECTION = "devel/python"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a565d8b5d06b9620968a135a2657b093"

SRC_URI[sha256sum] = "62869b23409349c21b072883ec8998316b234c9a9e36675756e8e317e8768f72"

RDEPENDS.${PN} = "${PYTHON_PN}-asyncio"

inherit setuptools3 pypi