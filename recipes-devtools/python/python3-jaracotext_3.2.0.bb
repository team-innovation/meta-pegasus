SUMMARY = "Python jaraco text."
DESCRIPTION = "Module for text manipulation"
HOMEPAGE = "https://github.com/jaraco/jaraco.text"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a33f38bbf47d48c70fe0d40e5f77498e"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "8085591783313b02afa591209b6e6ef5"
SRC_URI[sha256sum] = "e5078b1126cc0f166c7859aa75103a56c0d0f39ebcafc21695615472e0f810ec"

PYPI_PACKAGE = "jaraco.text"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

do_install_append() {
   rm -f ${D}/${libdir}/python${PYTHON_BASEVERSION}/site-packages/jaraco/__init__.py
   rm -f ${D}/${libdir}/python${PYTHON_BASEVERSION}/site-packages/jaraco/__pycache__/__init__.cpython-37.pyc
}


SYSROOT_DIRS_BLACKLIST +="${STAGING_LIBDIR_NATIVE}/python3.7/site-packages/jaraco/__init__.py \
                        ${STAGING_LIBDIR_NATIVE}/python3.7/site-packages/jaraco/__pycache__"
