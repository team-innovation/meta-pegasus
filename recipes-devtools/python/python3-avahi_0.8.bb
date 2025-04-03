SUMMARY = "Avahi - Service Discovery for Linux using mDNS/DNS-SD -- compatible with Bonjour"
HOMEPAGE = "https://github.com/avahi/avahi"
SECTION = "devel/python"

LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

SRC_URI = "https://github.com/avahi/avahi/releases/download/v0.8/avahi-0.8.tar.gz"
SRC_URI[sha256sum] = ""

inherit setuptools3-base