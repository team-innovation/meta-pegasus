SUMMARY = "Scapy: interactive packet manipulation tool"
HOMEPAGE = "https://github.com/secdev/scapy"
SECTION = "devel/python"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI[sha256sum] = "7600d7e2383c853e5c3a6e05d37e17643beebf2b3e10d7914dffcc3bc3c6e6c5"

RDEPENDS:${PN} += "libpcap"

inherit python_setuptools_build_meta pypi