# Copyright (C) 2016 Vivint Innovation 

DESCRIPTION = "SkyHub Utilities"
SECTION = "skyhub"
LICENSE = "CLOSED"
DEPENDS = ""
# PV = "${DISTRO_VERSION}"
PV = "1.0.0"
PR = "r0"

SRC_URI = "file://sshd_not_to_be_run"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RPROVIDES_${PN} = "sly-utils"

do_install() {
	install -d ${D}/${sysconfdir}/ssh
	install -m 644  ${WORKDIR}/sshd_not_to_be_run ${D}/${sysconfdir}/ssh/
}
