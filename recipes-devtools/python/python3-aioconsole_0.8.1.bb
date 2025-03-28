SUMMARY = "Asynchronous console and interfaces for asyncio"
HOMEPAGE = "https://pypi.org/project/aioconsole/"
SECTION = "devel/python"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1d26494164e98318558fe828e4505193"

RDEPENDS_${PN} = "${PYTHON_PN}-asyncio"

inherit setuptools3 pypi