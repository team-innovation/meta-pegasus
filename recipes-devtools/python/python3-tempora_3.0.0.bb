SUMMARY = "Python collections."
DESCRIPTION = "Python Models and classes to supplement the stdlib ‘collections’ module."
HOMEPAGE = "https://github.com/jaraco/jaraco.classes"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a33f38bbf47d48c70fe0d40e5f77498e"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "3675ccec0712ef4eb5834f1369f55007"
SRC_URI[sha256sum] = "e370d822cf48f5356aab0734ea45807250f5120e291c76712a1d766b49ae34f8"

PYPI_PACKAGE = "tempora"
inherit setuptools3 pypi

RDEPENDS_${PN} = " \
	${PYTHON_PN}-pytz \
	${PYTHON_PN}-jaracofunctools \
"
# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
