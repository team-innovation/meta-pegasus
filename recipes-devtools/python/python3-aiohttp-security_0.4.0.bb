DESCRIPTION = "Async http client/server framework"
HOMEPAGE = "https://pypi.org/project/aiohttp/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=933fc0498ca792547f5cdd6bdb8adc91"

DEPENDS += "python3 python3-aiohttp"

PR = "r1"
SRCNAME = "aiohttp-security"

SRC_URI[md5sum] = "1624f982d7a779e6ed396ef2ed20acc7"
SRC_URI[sha256sum] = "40d4bb150454e392c2dfa3620f6eab28e140e94cf0c2d3b4bf43aef653ec9405"

SRC_URI = "https://files.pythonhosted.org/packages/36/01/d85be376b7c1773b3cb7849cd56dc7d38165664df7de2d3e20af507ef5bb/${SRCNAME}-${PV}.tar.gz"

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
