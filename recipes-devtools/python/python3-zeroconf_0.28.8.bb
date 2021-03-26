DESCRIPTION = "Python Zeroconf"
SECTION = "devel/python"
LICENSE = "LGPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=27818cd7fd83877a8e3ef82b82798ef4"
PR = "r2"
SRCNAME = "zeroconf"

DEPENDS += "python3"
RDEPENDS_${PN} = "\
    python3-ifaddr \
"

SRC_URI = "https://files.pythonhosted.org/packages/4b/21/a09b4a8fdd0f9068c8134de31c4258f43c1c35352e89bdcb8994dae180d9/${SRCNAME}-${PV}.tar.gz"

SRC_URI[md5sum] = "0584aaa13e7d13999ad38517364e35d0"

S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit setuptools3 python3-dir
