DESCRIPTION = "Yet another URL library"
HOMEPAGE = "https://pypi.org/project/yarl/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b334fc90d45983db318f54fd5bf6c90b"

DEPENDS += "python3"

PR = "r1"
SRCNAME = "yarl"

SRC_URI = "https://files.pythonhosted.org/packages/43/b8/057c3e5b546ff4b24263164ecda13f6962d85c9dc477fcc0bcdcb3adb658/yarl-1.2.6.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "43380667129ebc52ac3c442daabb3f6d"
SRC_URI[sha256sum] = "c8cbc21bbfa1dd7d5386d48cc814fe3d35b80f60299cdde9279046f399c3b0d8"

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
