DESCRIPTION = "Fast implementation of asyncio event loop on top of libuv"
HOMEPAGE = "https://pypi.org/project/uvloop/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE-MIT;md5=3bd0b2e751776b370b2151ac508af939"

DEPENDS += "python3"

PR = "r2"
SRCNAME = "uvloop"

SRC_URI = "https://files.pythonhosted.org/packages/f0/5a/cf8cb64bdc694462ef1ee9a925302dac40b29a52ab0eede4bab7fc0e0f2b/${SRCNAME}-${PV}.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "3f57bccd1b37de92cb4a432415f9704e"
SRC_URI[sha256sum] = "afdf34bf507090e4c7f5108a17240982760356b8aae4edd37180ec4f94c36cbb"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

do_compile_prepend() {
	export LIBUV_CONFIGURE_HOST=${HOST_SYS}
}

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

do_package_qa() {
    echo "Skipping QA ..."
}

inherit setuptools3 python3-dir
