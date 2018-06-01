DESCRIPTION = "Implementation of OpenBSD Blowfish password hashing algorithm"
HOMEPAGE = "https://pypi.python.org/pypi/py3k-bcrypt"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://README;md5=0d1f36edfd186223c435e40665f58cf4"

PR = "r1"
SRCNAME = "py3k-bcrypt-${PV}"

SRC_URI = "https://pypi.python.org/packages/source/p/py3k-bcrypt/py3k-bcrypt-0.3.tar.gz"

#		   file://fixup_setuptool_to_distutils.patch"

SRC_URI[md5sum] = "8821f1190bf8f59ced4ed84b2a114ad2"
SRC_URI[sha256sum] = "4c1c0ddb03dceae2d3f0295af3e8688d283e7f56296a5ce812d81bd86f27f697"

S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
