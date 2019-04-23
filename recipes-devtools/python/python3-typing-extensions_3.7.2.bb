DESCRIPTION = "Python Typing Extensions"
HOMEPAGE = "https://pypi.org/project/typing-extensions/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=64fc2b30b67d0a8423c250e0386ed72f"

DEPENDS += "python3"

PR = "r1"
SRCNAME = "typing_extensions"

SRC_URI = "https://files.pythonhosted.org/packages/fa/aa/229f5c82d17d10d4ef318b5c22a8626a1c78fc97f80d3307035cf696681b/typing_extensions-3.7.2.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "33815f263f3e00ec63b9d9d26eea95de"
SRC_URI[sha256sum] = "fb2cd053238d33a8ec939190f30cfd736c00653a85a2919415cecf7dc3d9da71"

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
