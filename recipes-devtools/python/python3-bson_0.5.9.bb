DESCRIPTION = "Python bson"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://README.rst;md5=c1042b7bd704f64fe194d5bc5da9a359"
PR = "r1"

SRC_URI[md5sum] = "6fe1509f7747b08fc2cbaf87013dd42b"
SRC_URI[sha256sum] = "84a06dd1fa350d5814d9845abad982e8ac4c9768c4f41afeaa5fdb6d58570158"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit pypi setuptools3 python3-dir
