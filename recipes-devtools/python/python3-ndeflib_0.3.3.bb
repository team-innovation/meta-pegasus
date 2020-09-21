SUMMARY = "Python NDEF Library"
HOMEPAGE = "https://github.com/kichik/pyndef"
SECTION = "devel/python"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f7c92777f3af9604e192a0d195b6a6a4"

SRC_URI[md5sum] = "bb8e260e060acc0c81eb943326b86beb"
SRC_URI[sha256sum] = "1d56828558b9b16f2822a4051824346347b66adf5320ea86070748b6f2454a88"

PYPI_PACKAGE = "ndeflib"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
