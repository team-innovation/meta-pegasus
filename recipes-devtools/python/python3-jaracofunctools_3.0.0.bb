SUMMARY = "Python jaraco functools."
DESCRIPTION = "Functools like those found in stdlib"
HOMEPAGE = "https://github.com/jaraco/jaraco.text"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a33f38bbf47d48c70fe0d40e5f77498e"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "7ba7a7c47369879e0c3bdf083f34cc4c"
SRC_URI[sha256sum] = "5cb0eea0f254584241c519641328a4d4ec2001a86c3cd6d17a8fd228493f6d97"

PYPI_PACKAGE = "jaraco.functools"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"


SYSROOT_DIRS_BLACKLIST +="${STAGING_LIBDIR_NATIVE}/python3.7/site-packages/jaraco/__init__.py \
                        ${STAGING_LIBDIR_NATIVE}/python3.7/site-packages/jaraco/__pycache__"
