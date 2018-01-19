DESCRIPTION = "dateutil for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fa9d2e9d9366cd8d539240d9416d8465"
SRCNAME = "python-dateutil"
PR = "ml"

SRC_URI = " \
	http://labix.org/download/python-dateutil/${SRCNAME}-${PV}.tar.gz \
"


S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3

RDEPENDS_${PN} = "\
  python3-distutils \
"

SRC_URI[md5sum] = "22297f7e891bcd79a80d9446d8b20542"
SRC_URI[sha256sum] = "8aab12d0e944b33b5e347bcf1963819d8ad925c0617e9257322ef475760c0aab"

