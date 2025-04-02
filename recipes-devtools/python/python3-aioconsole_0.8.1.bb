SUMMARY = "Asynchronous console and interfaces for asyncio"
HOMEPAGE = "https://github.com/vxgmichel/aioconsole"
SECTION = "devel/python"

LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=783b7e40cdfb4a1344d15b1f7081af66"

SRC_URI[sha256sum] = "a3e52428d32623c96746ec3862d97483c61c12a2f2dfba618886b709415d4533"

S = "${WORKDIR}/aioconsole-${PV}"

RDEPENDS.${PN} = "${PYTHON_PN}-asyncio"

inherit setuptools3-base pypi