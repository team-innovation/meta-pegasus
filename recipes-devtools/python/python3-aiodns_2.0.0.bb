DESCRIPTION = "Simple DNS resolver for asyncio"
HOMEPAGE = "https://pypi.org/project/aiodns/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a565d8b5d06b9620968a135a2657b093"

DEPENDS += "python3"

PR = "r2"
SRCNAME = "aiodns"
SRC_URI[md5sum] = "3e121f9eb7ef3ba3556ba7ec28c6f63a"
SRC_URI[sha256sum] = "815fdef4607474295d68da46978a54481dd1e7be153c7d60f9e72773cd38d77d"
SRC_URI = "https://files.pythonhosted.org/packages/30/2e/b86ce168485b68d40c6a810838669deacf0abf41845c383659c2b613e69f/${SRCNAME}-${PV}.tar.gz"

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
