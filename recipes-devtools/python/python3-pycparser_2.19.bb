DESCRIPTION = "pycparser"
HOMEPAGE = "https://pypi.org/project/pycparser"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86f1cedb4e6410a88ce8e30b91079169"

DEPENDS += "python3"
#RDEPENDS_${PN} = ""

PR = "r0"

SRCNAME = "pycparser"
SRC_URI = "https://files.pythonhosted.org/packages/68/9e/49196946aee219aead1290e00d1e7fdeab8567783e83e1b9ab5585e6206a/${SRCNAME}-${PV}.tar.gz"

SRC_URI[md5sum] = "76396762adc3fa769c83d8e202d36b6f"
SRC_URI[sha256sum] = "a988718abfad80b6b157acce7bf130a30876d27603738ac39f140993246b25b3"

S = "${WORKDIR}/${SRCNAME}-${PV}"

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
