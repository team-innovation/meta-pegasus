DESCRIPTION = "Patch ssl.match_hostname for Unicode(idna) domains support"
HOMEPAGE = "https://pypi.org/project/idna_ssl/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a61b9c5aec8796b64a6bf15d42605073"

DEPENDS += "python3"

PR = "r1"
SRCNAME = "idna-ssl"

SRC_URI = "https://files.pythonhosted.org/packages/c4/3b/facf5a5009e577e7764e68a2af5ee25c63f41c78277260c2c42b8cfabf2e/idna-ssl-1.0.1.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "a7fc74e9530f0494cb75ca6486771832"
SRC_URI[sha256sum] = "1293f030bc608e9aa9cdee72aa93c1521bbb9c7698068c61c9ada6772162b979"

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
