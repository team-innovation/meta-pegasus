DESCRIPTION = "Python unit test"
SECTION = "devel/python"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "\
   file://lgpl.txt;md5=a6f89e2100d9b6cdffcea4f398e37343 \
"

PR = "ml2"

DEPENDS = "python3"

SRC_URI = "https://pypi.python.org/packages/source/n/nose/nose-${PV}.tar.gz \
	   file://fix_url.patch \
	   "

S = "${WORKDIR}/nose-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

do_install_append() {
   rm -f ${D}/${libdir}/${PYTHON_DIR}/site-packages/__pycache__/site.cpython-33.pyc
   rm -f ${D}/${libdir}/${PYTHON_DIR}/site-packages/site.py
   rm -f ${D}/${libdir}/${PYTHON_DIR}/site-packages/setuptools.pth
   rm -f ${D}/${bindir}/easy_install-3.5
}

SRC_URI[md5sum] = "4d3ad0ff07b61373d2cefc89c5d0b20b"
SRC_URI[sha256sum] = "f1bffef9cbc82628f6e7d7b40d7e255aefaa1adb6a1b1d26c69a8b79e6208a98"
