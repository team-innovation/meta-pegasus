DESCRIPTION = "pyopenssl"
HOMEPAGE = "https://pypi.org/project/pyopenssl"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PYPI_PACKAGE = "pyOpenSSL"
DEPENDS += "python3-cryptography"
RDEPENDS_${PN} = "python3-six"

PR = "r1"

SRC_URI[md5sum] = "b9876625dc1d5a5a662d748689191537"
SRC_URI[sha256sum] = "aeca66338f6de19d1aa46ed634c3b9ae519a64b458f8468aec688e7e3c20f200"

S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"


inherit setuptools3 pypi
