SUMMARY = "Asynchronous console and interfaces for asyncio"
HOMEPAGE = "https://pypi.org/project/aioconsole/"
SECTION = "devel/python"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1d26494164e98318558fe828e4505193"

SRC_URI[sha256sum] = "0535ce743ba468fb21a1ba43c9563032c779534d4ecd923a46dbd350ad91d234"

RDEPENDS_${PN} = "${PYTHON_PN}-asyncio"

inherit setuptools3 pypi