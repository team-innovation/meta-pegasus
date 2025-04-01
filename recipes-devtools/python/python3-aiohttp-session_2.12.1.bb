SUMMARY = "sessions for aiohttp.web"
HOMEPAGE = "https://github.com/aio-libs/aiohttp-session"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9eaa7e596ac33f220516bc368102250e"

PYPI_PACKAGE = "aiohttp_session"
SRC_URI[sha256sum] = "15e6e0288e9bcccd4b1d0c28aae9c20e19a252b12d0cb682223ca9c83180e899" 

RDEPENDS.${PN} = "${PYTHON_PN}-aiohttp"

inherit setuptools3 pypi