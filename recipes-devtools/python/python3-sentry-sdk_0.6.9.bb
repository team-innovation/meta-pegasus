DESCRIPTION = "Python sentry"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0c79f8d3c91fc847350efd28bfe0a341"
PR = "r1"
SRCNAME = "sentry-sdk"

SRC_URI = "https://files.pythonhosted.org/packages/de/26/58c551a0b4cad56498df1a21dd5411c4fea66be58573aa381982f443d28c/sentry-sdk-0.6.9.tar.gz"

SRC_URI[md5sum] = "33bfdd7541c17726c19925cec9f8fd07"
SRC_URI[sha256sum] = "78bb79adfe991a770ce2179826f5f216e086bbc4bd4585c543f377180cae3654"

S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
