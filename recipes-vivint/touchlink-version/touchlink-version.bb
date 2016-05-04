# Copyright (C) 2015 Vivint Innovation 

DESCRIPTION = "Fake Touchlink Version Information"
SECTION = "touchlink"
LICENSE = "CLOSED"
DEPENDS = ""
PV = "${DISTRO_VERSION}"
PR = "r0"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

do_install() {
    install -d ${D}${sysconfdir}
    echo "3.0.0.12345" > ${D}${sysconfdir}/touchlink-version
}
