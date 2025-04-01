SUMMARY = "sessions for aiohttp.web"
HOMEPAGE = "https://github.com/aio-libs/aiohttp-session"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

SRC_URI = "https://files.pythonhosted.org/packages/c2/c4/d73a7f19b1bd3149ba5bccd22e3ab580c19e4d9fcb83114309e8385ab807/aiohttp_session-2.12.1.tar.gz"
SRC_URI[sha256sum] = "15e6e0288e9bcccd4b1d0c28aae9c20e19a252b12d0cb682223ca9c83180e899" 

RDEPENDS.${PN} = "${PYTHON_PN}-aiohttp"

inherit setuptools3 pypi

do_fetch.prepend() {
    bb.note(d.getVar('SRC_URI'))
}
