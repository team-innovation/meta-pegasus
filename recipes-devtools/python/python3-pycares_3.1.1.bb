DESCRIPTION = "Python interface for c-ares"
HOMEPAGE = "https://pypi.org/project/pycares/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b1538fcaea82ebf2313ed648b96c69b1"

DEPENDS += "python3"

PR = "r2"
SRCNAME = "pycares"

SRC_URI = "https://files.pythonhosted.org/packages/4e/09/f49ef1c4b6a5ad50fc08a8acd015f1938594dd7a6b4a6a96d049d9bbec7d/pycares-3.1.1.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"
SRC_URI[md5sum] = "8ac802d79b318efa27d3a9949d0604d1"
SRC_URI[sha256sum] = "18dfd4fd300f570d6c4536c1d987b7b7673b2a9d14346592c5d6ed716df0d104"

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
