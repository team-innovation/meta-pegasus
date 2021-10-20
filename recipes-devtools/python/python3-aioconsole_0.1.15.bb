DESCRIPTION = "Asynchronous console and interfaces for asyncio"
HOMEPAGE = "https://pypi.org/project/aioconsole/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.rst;md5=7272a2301b319f360c9b583cc9020fa7"

DEPENDS += "python3"

PR = "r3"
SRCNAME = "aioconsole"

SRC_URI = "https://files.pythonhosted.org/packages/c4/68/56b09bf94aff8e02a6080493b3f85bdf7ddea7e4f264e6a90a158e064898/${SRCNAME}-${PV}.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "918caf70f00a3fbc8a61cd74579da1c4"
SRC_URI[sha256sum] = "498e567805b74c1ecf4501d28e48727e8e77af8d6a0b8f1b7aa9a4842e02743d"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

do_package_qa() {
    echo "Skipping QA ..."
}

inherit setuptools3 python3-dir
