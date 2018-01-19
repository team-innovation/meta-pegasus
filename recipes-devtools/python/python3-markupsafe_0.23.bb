DESCRIPTION = "Python MarkupSafe"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c6d1adcf45d69359f256c1cea3254127"
PR = "2"

DEPENDS += "python3"
RDEPENDS_${PN} = "python3-core"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"
NATIVE_INSTALL_WORKS = "1"

SRC_URI = "https://pypi.python.org/packages/source/M/MarkupSafe/MarkupSafe-${PV}.tar.gz \
	   "
S = "${WORKDIR}/MarkupSafe-${PV}"

inherit setuptools3

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

SRC_URI[md5sum] = "f5ab3deee4c37cd6a922fb81e730da6e"
SRC_URI[sha256sum] = "a4ec1aff59b95a14b45eb2e23761a0179e98319da5a7eb76b56ea8cdc7b871c3"


