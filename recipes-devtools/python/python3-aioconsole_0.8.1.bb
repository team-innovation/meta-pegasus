SUMMARY = "Asynchronous console and interfaces for asyncio"
HOMEPAGE = "https://github.com/vxgmichel/aioconsole"
SECTION = "devel/python"

LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

SRC_URI[sha256sum] = "0535ce743ba468fb21a1ba43c9563032c779534d4ecd923a46dbd350ad91d234"

S = "${WORKDIR}/aioconsole-${PV}"

RDEPENDS.${PN} = "${PYTHON_PN}-asyncio"

inherit setuptools3-base pypi