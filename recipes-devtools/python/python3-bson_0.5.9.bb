DESCRIPTION = "Python bson"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://README.rst;md5=c1042b7bd704f64fe194d5bc5da9a359"
PR = "r1"
SRCNAME = "bson"

SRC_URI = "https://files.pythonhosted.org/packages/68/dd/fe19add4f6f6d6e8cac5f00f62ff33892bbf93bf23669501c75a63346d70/bson-0.5.9.tar.gz"

SRC_URI[md5sum] = "6fe1509f7747b08fc2cbaf87013dd42b"
SRC_URI[sha256sum] = "84a06dd1fa350d5814d9845abad982e8ac4c9768c4f41afeaa5fdb6d58570158"

S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
