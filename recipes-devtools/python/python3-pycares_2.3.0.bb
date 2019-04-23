DESCRIPTION = "Python interface for c-ares"
HOMEPAGE = "https://pypi.org/project/pycares/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b1538fcaea82ebf2313ed648b96c69b1"

DEPENDS += "python3"

PR = "r1"
SRCNAME = "pycares"

SRC_URI = "https://files.pythonhosted.org/packages/89/5c/3a7e1a52d6abb52b9ca1a56d2df699936e89d8b98f75cfd60d03363e7c10/pycares-2.3.0.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"
SRC_URI[md5sum] = "74893b2b380fbb45329a406ae4b1ae89"
SRC_URI[sha256sum] = "36f4c03df57c41a87eb3d642201684eb5a8bc194f4bafaa9f60ee6dc0aef8e40"

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
