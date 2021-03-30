DESCRIPTION = "Server-sent events support for aiohttp"
HOMEPAGE = "https://pypi.org/project/aiohttp-sse/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d2794c0df5b907fdace235a619d80314"

DEPENDS += "python3-aiohttp"

PR = "r1"
SRCNAME = "aiohttp-sse"

SRC_URI[md5sum] = "d2f394fb75f591045b2c94fc17c8533d"
SRC_URI[sha256sum] = "547e1eaa129749f090d02b31956215edbcde74ce99721f5f0ac902a9ccb1202e"


SRC_URI = "https://files.pythonhosted.org/packages/2b/50/e127729f7df53c32c96b5c71932a7262cad40c83f1e19c218b068c816d51/${SRCNAME}-${PV}.tar.gz"

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
