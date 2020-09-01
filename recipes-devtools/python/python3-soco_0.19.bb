SUMMARY = "Python SoCo"
HOMEPAGE = " https://github.com/SoCo/SoCo"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=07b0e2ca9ac77cd65cd4edf2e13367ea"

SRC_URI[md5sum] = "285b6987c48e9c7b59dd7e1f208472c8"
SRC_URI[sha256sum] = "93e1f3de65c94199b7013a2b7098e0e697846621454a92495d2ac36d9050ec35"

PYPI_PACAKGE = "soco"
inherit pypi setuptools3

RDEPENDS_${PN} = "\
  ${PYTHON_PN}-xmltodict \
  ${PYTHON_PN}-requests \
"

BBCLASSEXTEND = "native nativesdk"
