SUMMARY = "Python jaraco tempora."
DESCRIPTION = "Objects and routines pertaining to date and time (tempora)"
HOMEPAGE = "https://github.com/jaraco/jaraco.text"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a33f38bbf47d48c70fe0d40e5f77498e"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "10d1b74d9218dfaf6c53a2b3f28145c8"
SRC_URI[sha256sum] = "f11df59b34b9a87d395cce031274f6ff3d2be2171dd1db32dff9ab1fcb5352fa"

PYPI_PACKAGE = "tempora"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"


do_install_append() {
   rm -f ${D}/${libdir}/python${PYTHON_BASEVERSION}/site-packages/jaraco/__init__.py
   rm -f ${D}/${libdir}/python${PYTHON_BASEVERSION}/site-packages/jaraco/__pycache__/__init__.cpython-37.pyc
}


SYSROOT_DIRS_BLACKLIST +="${STAGING_LIBDIR_NATIVE}/python3.7/site-packages/jaraco/__init__.py \
                        ${STAGING_LIBDIR_NATIVE}/python3.7/site-packages/jaraco/__pycache__"
