SUMMARY = "Foreign Function Interface for Python calling C code"
HOMEPAGE = "http://cffi.readthedocs.org/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5677e2fdbf7cdda61d6dd2b57df547bf"
DEPENDS += "libffi ${PYTHON_PN}-pycparser"

inherit pypi setuptools3

SRC_URI[md5sum] = "d6d5c4805bbce844cf1368702b056e3c"
SRC_URI[sha256sum] = "9b6f7ba4e78c52c1a291d0c0c0bd745d19adde1a9e1c03cb899f0c6efd6f8033"

RDEPENDS_${PN}_class-target = " \
    ${PYTHON_PN}-ctypes \
    ${PYTHON_PN}-io \
    ${PYTHON_PN}-shell \
"

BBCLASSEXTEND = "native nativesdk"
