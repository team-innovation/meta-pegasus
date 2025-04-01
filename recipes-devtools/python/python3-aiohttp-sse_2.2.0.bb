SUMMARY = "Server-sent events support for aiohttp."
HOMEPAGE = "https://github.com/aio-libs/aiohttp-sse"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

SRC_URI[sha256sum] = "" 

RDEPENDS.${PN} = "${PYTHON_PN}-aiohttp"

inherit setuptools3 pypi