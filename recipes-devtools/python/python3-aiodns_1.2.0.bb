DESCRIPTION = "Simple DNS resolver for asyncio"
HOMEPAGE = "https://pypi.org/project/aiodns/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a565d8b5d06b9620968a135a2657b093"

DEPENDS += "python3"

PR = "r2"
SRCNAME = "aiodns"
SRC_URI[md5sum] = "b0fb02275cafcbe5ad531c72ba950615"
SRC_URI[sha256sum] = "d67e14b32176bcf3ff79b5d47c466011ce4adeadfa264f7949da1377332a0449"
SRC_URI = "https://files.pythonhosted.org/packages/7e/1b/f01e4d3502d1364e4df66e477c333bccac2361740e5688b9bc1826cc08bd/${SRCNAME}-${PV}.tar.gz"

SRCREV ="${AUTOREV}"
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
