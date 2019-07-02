DESCRIPTION = "A Pure Python subshell launcher for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a2749dd4bc0d891475a1f9d619c3bda5"
SRCNAME = "gnupg"
PR = "ml1"

SRC_URI = "https://files.pythonhosted.org/packages/96/6c/21f99b450d2f0821ff35343b9a7843b71e98de35192454606435c72991a8/gnupg-3.1.1.tar.gz"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit python3native python3-dir setuptools3 

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir

DEPENDS += "python python3 python3-native"
RDEPENDS_${PN} = "\
  python3-core \
  gnupg \
  python \
"

RDEPENDS_${PN}_class-native = ""

SRC_URI[md5sum] = "99622297b05b605a0a4e98f1b580186d"
SRC_URI[sha256sum] = "8db5a05c369dbc231dab4c98515ce828f2dffdc14f1534441a6c59b71c6d2031"

