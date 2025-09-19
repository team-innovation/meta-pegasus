SUMMARY = "Python library for Intel HEX files manipulations"
HOMEPAGE = "https://github.com/python-intelhex/intelhex"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=4eba844696655c3eae07aca8e3a94772"

SRC_URI[md5sum] = "9ce7402e0fd33249aef52cbf437b4ab7"
SRC_URI[sha256sum] = "009d8511e0d50639230c39af9607deee771cf026f67ef7507a8c3fd4fa927832"

PYPI_PACKAGE = "intelhex"
inherit pypi setuptools3

BBCLASSEXTEND = "native nativesdk"
