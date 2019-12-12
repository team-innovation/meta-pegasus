DESCRIPTION = "Timeout context manager for asyncio programs"
HOMEPAGE = "https://pypi.org/project/async_timeout/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

DEPENDS += "python3"

PR = "r1"
SRCNAME = "async-timeout"

SRC_URI = "https://files.pythonhosted.org/packages/35/82/6c7975afd97661e6115eee5105359ee191a71ff3267fde081c7c8d05fae6/async-timeout-3.0.0.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "0a7872948573f9ef17f9ecb484024c21"
SRC_URI[sha256sum] = "b3c0ddc416736619bd4a95ca31de8da6920c3b9a140c64dbef2b2fa7bf521287"

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
