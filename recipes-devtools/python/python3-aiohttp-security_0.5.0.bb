SUMMARY = "security for aiohttp.web"
HOMEPAGE = "https://github.com/aio-libs/aiohttp-security"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

SRC_URI[sha256sum] = "d6edc538c7480fa0a3b2bdd705f8010062d74700198da55d16498e1b49549b9c"

RDEPENDS.${PN} = "${PYTHON_PN}-aiohttp"

inherit setuptools3 pypi