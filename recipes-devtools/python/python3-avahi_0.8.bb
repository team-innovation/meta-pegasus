SUMMARY = "Avahi - Service Discovery for Linux using mDNS/DNS-SD -- compatible with Bonjour"
HOMEPAGE = "https://github.com/avahi/avahi"
SECTION = "devel/python"

LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

SRC_URI[sha256sum] = ""

do_install_append() {
    install -Dm 0644 ${S}/LICENSE ${D}${datadir}/licenses/${PN}/LICENSE
}

inherit setuptools3