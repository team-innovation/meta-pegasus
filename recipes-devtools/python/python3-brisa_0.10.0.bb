DESCRIPTION = "Python Brisa"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6b68f8ffe456195373f3cfb1bd43ef1a"
PR = "r1"
SRCNAME = "python3-brisa"

DEPENDS += "python3"
#DEPENDS_virtclass-native += "python3-native"
RDEPENDS_${PN} = "\
  python3-core \
  python3-requests \
  python3-cherrypy \
"

SRC_URI = "file://python3-brisa_0.10.0.tar.gz"

SRC_URI[md5sum] = "c080ee44a306e464ab5372626648bbdf"

S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
#BBCLASSEXTEND = "native"

#NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
