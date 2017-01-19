# Copyright (C) 2017 Vivint Innovation 

DESCRIPTION = "Glance Utilities"
SECTION = "glance"
LICENSE = "CLOSED"
DEPENDS = ""
# PV = "${DISTRO_VERSION}"
PV = "1.0.0"
PR = "r0"

SRC_URI = "\
	file://mfg_test \
"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"
S = "${WORKDIR}"

RPROVIDES_${PN} = "slimline-utils"

do_install() {
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${S}/mfg_test ${D}/${sysconfdir}/init.d/mfg_test
    update-rc.d -r ${D} mfg_test start 86 5 . 
}
