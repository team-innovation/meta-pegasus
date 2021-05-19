DESCRIPTION = "Python YAML library"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e04f70f331d3fc1e7623959f9a48244c"
PR = "3.5.1"
SRCNAME = "PyYAML"

DEPENDS += "python3"
RDEPENDS_${PN} = "\
"
SRC_URI = "https://files.pythonhosted.org/packages/64/c2/b80047c7ac2478f9501676c988a5411ed5572f35d1beff9cae07d321512c/${SRCNAME}-${PV}.tar.gz"

SRC_URI[md5sum] = "d3590b85917362e837298e733321962b"

S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"


inherit setuptools3 python3-dir
