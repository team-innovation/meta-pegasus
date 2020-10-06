DESCRIPTION = "Python JWT implementation"
HOMEPAGE = "https://pypi.org/project/PyJWT/"
SECTION = "devel/python"
LICENSE = "MIT License (MIT)"
LIC_FILES_CHKSUM = "file://LICENSE;md5=68626705a7b513ca8d5f44a3e200ed0c"

DEPENDS += "python3"

PR = "r1"
SRCNAME = "PyJWT"

SRC_URI[md5sum] = "a4712f980c008696e13e09504120b2a0"
SRC_URI[sha256sum] = "8d59a976fb773f3e6a39c85636357c4f0e242707394cadadd9814f5cbaa20e96"

SRC_URI = "https://files.pythonhosted.org/packages/2f/38/ff37a24c0243c5f45f5798bd120c0f873eeed073994133c084e1cf13b95c/${SRCNAME}-${PV}.tar.gz"

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
