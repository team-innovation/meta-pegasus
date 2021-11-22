SUMMARY = "Light-weight Python dict"
HOMEPAGE = "https://github.com/nnemkin/sparsedict"
LICENSE = "PSF"
SECTION = "devel/python"
SRCNAME = "sparsedict"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5afd36d4fa6a787b36a0313a4b803424"
PV = "1.0.0rc1+gitr${SRCREV}"

SRCREV = "f1c1c55ae40c1514ff5c075e06da934b4f374916"
SRC_URI = "git://github.com/nnemkin/sparsedict.git;protocol=https;branch=master"
S = "${WORKDIR}/git"

inherit setuptools3 

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
