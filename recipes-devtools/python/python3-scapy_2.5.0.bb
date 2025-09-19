SUMMARY = "Python interactive packet manipulation"
DESCRIPTION = "Scapy is a powerful Python-based interactive packet manipulation program and library."
HOMEPAGE = "https://pypi.org/project/scapy/"
SECTION = "devel/python"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"
PR = "r1"
SRC_NAME = "scapy"

RDEPENDS_${PN} += " \
	libpcap \
"

SRC_URI[md5sum] = "f546b1c82c2c98e0dae1a436219dd042"
SRC_URI[sha256sum] = "5b260c2b754fd8d409ba83ee7aee294ecdbb2c235f9f78fe90bc11cb6e5debc2"
SRC_URI = "https://files.pythonhosted.org/packages/67/a1/2a60d5b6f0fed297dd0c0311c887d5e8a30ba1250506585b897e5a662f4c/${SRC_NAME}-${PV}.tar.gz"

PYPI_PACKAGE = "scapy"
inherit setuptools3 pypi

BBCLASSEXTEND = "native nativesdk"
