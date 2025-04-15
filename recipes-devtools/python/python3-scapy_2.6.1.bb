SUMMARY = "Scapy: interactive packet manipulation tool"
HOMEPAGE = "https://github.com/secdev/scapy"
SECTION = "devel/python"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

SRC_URI[sha256sum] = "5b260c2b754fd8d409ba83ee7aee294ecdbb2c235f9f78fe90bc11cb6e5debc2"

RDEPENDS:${PN} += "libpcap"

inherit python_setuptools_build_meta pypi