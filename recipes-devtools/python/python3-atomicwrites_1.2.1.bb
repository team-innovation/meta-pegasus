DESCRIPTION = "Python Atomic file writes."
HOMEPAGE = "https://pypi.org/project/atomicwrites"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=91cc36cfafeefb7863673bcfcb1d4da4"

PR = "r1"
SRCNAME = "atomicwrites-${PV}"

SRC_URI = "https://files.pythonhosted.org/packages/ac/ed/a311712ef6b4355035489f665e63e1a73f9eb371929e3c98e5efd451069e/atomicwrites-1.2.1.tar.gz"

S = "${WORKDIR}/${SRCNAME}"

SRC_URI[md5sum] = "9b64377c3f93e9877adc4460e9984f2b"
SRC_URI[sha256sum] = "ec9ae8adaae229e4f8446952d204a3e4b5fdd2d099f9be3aaf556120135fb3ee"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
