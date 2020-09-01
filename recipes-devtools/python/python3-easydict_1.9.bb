SUMMARY  = "EasyDict allows to access dict values as attributes"
DESCRIPTION  = "EasyDict allows to access dict values as attributes. A Javascript-like properties dot notation for python dicts."
HOMEPAGE = "http://github.com/makinacorpus/easydict"
SECTION = "devel/python"
LICENSE = "LGPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

SRC_URI[md5sum] = "8744c50bf7964bbe635011c1210a3b81"
SRC_URI[sha256sum] = "3f3f0dab07c299f0f4df032db1f388d985bb57fa4c5be30acd25c5f9a516883b"

PYPI_PACKAGE = "easydict"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
