DESCRIPTION = "Python six - compatibilty library between python2 and python3 "
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6f00d4a50713fa859858dd9abaa35b21"

PR = "r1"
SRCNAME = "six"

SRC_URI = "https://pypi.python.org/packages/source/s/${SRCNAME}/${SRCNAME}-${PV}.tar.gz"

#		   file://fixup_setuptool_to_distutils.patch"

SRC_URI[md5sum] = "34eed507548117b2ab523ab14b2f8b55"
SRC_URI[sha256sum] = "105f8d68616f8248e24bf0e9372ef04d3cc10104f1980f54d57b2ce73a5ad56a"

S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir

do_install_append() {
   rm -f ${D}/${libdir}/${PYTHON_DIR}/site-packages/__pycache__/site.cpython-33.pyc
   rm -f ${D}/${libdir}/${PYTHON_DIR}/site-packages/site.py
}

