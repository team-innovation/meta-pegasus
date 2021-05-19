DESCRIPTION = "This is fork of pyzeroconf, Multicast DNS Service Discovery for Python"
HOMEPAGE = "https://pypi.org/project/zeroconf"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=27818cd7fd83877a8e3ef82b82798ef4"

PR = "r1"

SRC_URI[md5sum] = "d2ee623e6dacd895a926ea374d897120"
SRC_URI[sha256sum] = "53a180248471c6f81bd1fffcbce03ed93d7d8eaf10905c9121ac1ea996d19844"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"


inherit setuptools3 pypi
