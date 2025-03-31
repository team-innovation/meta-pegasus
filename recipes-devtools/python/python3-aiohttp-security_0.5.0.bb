SUMMARY = "security for aiohttp.web"
HOMEPAGE = "https://github.com/aio-libs/aiohttp-security"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

SRC_URI[sha256sum] = "50cb724d310e1d0289798bb98ae852c3b75b77e8f51ce5159c57b7261d0638bd"

RDEPENDS.${PN} = "${PYTHON_PN}-aiohttp"

inherit setuptools3 pypi