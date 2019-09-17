DESCRIPTION = "Asynchronous console and interfaces for asyncio"
HOMEPAGE = "https://pypi.org/project/aioconsole/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.rst;md5=7272a2301b319f360c9b583cc9020fa7"

DEPENDS += "python3"

PR = "r2"
SRCNAME = "aioconsole"

SRC_URI = "https://files.pythonhosted.org/packages/83/4d/392f687992516a71eee1bad90002714938ad82d4bec455f34d0bf86b2e12/${SRCNAME}-${PV}.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "019845651ddd50d119c3222a5d20e142"
SRC_URI[sha256sum] = "8c009bb38b67beb018f301e1e032f78dc4ab86dab5c8a782462cb16adde94e76"

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
