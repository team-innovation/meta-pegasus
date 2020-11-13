DESCRIPTION = "Async http client/server framework"
HOMEPAGE = "https://pypi.org/project/aiohttp/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c82758543767c96574b6e29fc478fb73"

DEPENDS += "python3 python3-aiohttp"

PR = "r1"
SRCNAME = "aiohttp-session"

SRC_URI[md5sum] = "993ec277ece4765472d0edc0f6c0bba3"
SRC_URI[sha256sum] = "959413468b84e30e7ca09719617cfb0000066a2e0f6c20062d043433e82aeb74"

SRC_URI = "https://files.pythonhosted.org/packages/f8/fe/53dfd35f5c7fcc7f2d0866cb29e722303e3fae7f749c1f3d4d11d361dc38/${SRCNAME}-${PV}.tar.gz"

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
