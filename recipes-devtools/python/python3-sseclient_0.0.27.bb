DESCRIPTION = "Python SSE Client"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e04f70f331d3fc1e7623959f9a48244c"
PR = "r2"
SRCNAME = "sseclient"

DEPENDS += "python3"
RDEPENDS_${PN} = "\
"
SRC_URI = "https://files.pythonhosted.org/packages/cf/6c/fa601216344952be8f9a51a3f49b4274bbbc58bd395f87f67f6131726358/${SRCNAME}-${PV}.tar.gz"

SRC_URI[md5sum] = "f512abac1dda6d096f7f62714e4b83ae"

S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"


inherit setuptools3 python3-dir
