DESCRIPTION = "Websockets"
HOMEPAGE = "https://pypi.org/project/websockets"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5070256738c06d2e59adbec1f4057dac"

DEPENDS += "python3"
RDEPENDS_${PN} = ""

PR = "r1"

SRCNAME = "websockets-${PV}"
SRC_URI = "https://files.pythonhosted.org/packages/4e/2a/56e60bb4c3696bc736998cc13c3fa1a36210609d7e1a3f2519857b420245/${SRCNAME}.tar.gz"
SRC_URI[md5sum] = "76cf931a525a3415f5a4f59c133e89c3"
SRC_URI[sha256sum] = "8f3b956d11c5b301206382726210dc1d3bee1a9ccf7aadf895aaf31f71c3716c"

S = "${WORKDIR}/${SRCNAME}"

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
