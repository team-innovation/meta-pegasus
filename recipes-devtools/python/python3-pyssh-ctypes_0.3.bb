SUMMARY = "Python bindings for libssh on top of ctypes"
HOMEPAGE = "https://pypi.python.org/pypi/pyssh-ctypes/0.3"
SECTION = "devel/python"
LICENSE = "BSD"
#not right
LIC_FILES_CHKSUM = "file://README.rst;md5=29ac2c60ecada9c125781fc6975494e6"

PR = "r2"

SRC_URI[md5sum] = "4abc3cdd0afe542c93ef2aa1d7eadf61"
SRC_URI[sha256sum] = "5cb795dd920543b58768402149e73d73c326277d93c26b54d812e99469f44290"


#Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit setuptools3 pypi

RDEPENDS_${PN} = "libssh"
