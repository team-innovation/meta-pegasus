SUMMARY = "Python bindings for libssh on top of ctypes"
HOMEPAGE = "https://github.com/niwinz/py-libssh"
SECTION = "devel/python"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://setup.py;md5=b1818b95879748539e0fe870fd4975e0"

SRC_URI[sha256sum] = "5cb795dd920543b58768402149e73d73c326277d93c26b54d812e99469f44290"

RDEPENDS:${PN} = "libssh"

inherit setuptools3 pypi