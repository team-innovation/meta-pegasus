SUMMARY = "Python interactive packet manipulation"
DESCRIPTION = "Scapy is a powerful Python-based interactive packet manipulation program and library."
HOMEPAGE = "https://pypi.org/project/scapy/"
SECTION = "devel/python"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"
PR = "r1"
SRC_NAME = "scapy"

SRC_URI[md5sum] = "54072af118e9291b6c7898b4ea87cdaf"
SRC_URI[sha256sum] = "bc707e3604784496b6665a9e5b2a69c36cc9fb032af4864b29051531b24c8593"
SRC_URI = "https://files.pythonhosted.org/packages/85/47/c919432ca258f354bb2c1e645623f891603f185bfc7563d4a21f6432e7ed/${SRC_NAME}-${PV}.tar.gz"

PYPI_PACKAGE = "scapy"
inherit setuptools3 pypi

BBCLASSEXTEND = "native nativesdk"