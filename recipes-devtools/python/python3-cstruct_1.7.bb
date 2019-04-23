DESCRIPTION = "C-style structs for Python"
HOMEPAGE = "https://pypi.org/project/cstruct/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.md;md5=c82719bb922baaf3b4238bc7e88f274d"

DEPENDS += "python3"

PR = "r1"
SRCNAME = "cstruct"

SRC_URI = "https://files.pythonhosted.org/packages/f2/d0/d10590ffcda995237cf13c3b90dba1172aa3cdf5f7b01eb35f7c491485b1/cstruct-1.7.tar.gz"
S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "0d90ebc9396b15f8703263f8e0fa40a3"
SRC_URI[sha256sum] = "42c6239336fe2d7119941ac3f9ca360e07148d715b742231e3dcc3a5ddd76492"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

do_package_qa() {
    echo "Skipping QA ..."
}

inherit setuptools3 python3-dir
