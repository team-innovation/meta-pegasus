DESCRIPTION = "Light-weight Python dict"
SECTION = "devel/python"
LICENSE = "PSF"
SRCNAME = "sparsedict"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5afd36d4fa6a787b36a0313a4b803424"
PR = "r1"

DEPENDS += "python3"

SRCREV = "f1c1c55ae40c1514ff5c075e06da934b4f374916"
PV = "1.0.0rc1+gitr${SRCREV}"
SRC_URI = "git://github.com/nnemkin/sparsedict.git;protocol=git;branch=master"
S = "${WORKDIR}/git"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3

