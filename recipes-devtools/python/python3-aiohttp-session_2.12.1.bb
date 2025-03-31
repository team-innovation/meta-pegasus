SUMMARY = "sessions for aiohttp.web"
HOMEPAGE = "https://github.com/aio-libs/aiohttp-session"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

SRC_URI[sha256sum] = ""

SRC_URI = "https://files.pythonhosted.org/packages/c2/c4/d73a7f19b1bd3149ba5bccd22e3ab580c19e4d9fcb83114309e8385ab807/${SRCNAME}-${PV}.tar.gz"
S = "${WORKDIR}/${SRCNAME}-${PV}"

RDEPENDS.${PN} = "${PYTHON_PN}-aiohttp"

inherit setuptools3 pypi