DESCRIPTION = "Generate simple tables in terminals from a nested list of strings."
HOMEPAGE = "https://pypi.org/project/terminaltables/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://README.rst;md5=71891cdcd37ada308a75a0c0efd60216"

DEPENDS += "python3"

PR = "r1"
SRCNAME = "terminaltables"

SRC_URI = "https://files.pythonhosted.org/packages/9b/c4/4a21174f32f8a7e1104798c445dacdc1d4df86f2f26722767034e4de4bff/terminaltables-3.1.0.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "863797674d8f75d22e16e6c1fdcbeb41"
SRC_URI[sha256sum] = "f3eb0eb92e3833972ac36796293ca0906e998dc3be91fbe1f8615b331b853b81"

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
