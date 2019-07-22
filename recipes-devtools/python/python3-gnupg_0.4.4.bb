DESCRIPTION = "Python interface to gnupg"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "BSD-2-Clause"
SRCNAME = "python-gnupg"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b0b7ac63b60085b23fa9f7e1951daa1d"
PR = "ml1"

DEPENDS += "python3"
RDEPENDS_${PN} = "python3-core gnupg"

SRC_URI = "https://pypi.python.org/packages/source/p/python-gnupg/${SRCNAME}-${PV}.tar.gz"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
SRC_URI[md5sum] = "c5d54f5a1bafc412f9b5a03ee06cb82e"
SRC_URI[sha256sum] = "45daf020b370bda13a1429c859fcdff0b766c0576844211446f9266cae97fb0e"

S = "${WORKDIR}/${SRCNAME}-${PV}"
