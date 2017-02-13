# Copyright (C) 2016 Vivint Innovation 

DESCRIPTION = "SkyHub Utilities"
SECTION = "skyhub"
LICENSE = "CLOSED"
DEPENDS = ""
# PV = "${DISTRO_VERSION}"
PV = "1.0.0"
PR = "r2"

SRC_URI = "\
	file://mfg_test \
	file://sshd_not_to_be_run \
"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"
S = "${WORKDIR}"

RPROVIDES_${PN} = "sly-utils"

do_install() {
    install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${S}/mfg_test ${D}/${sysconfdir}/init.d/mfg_test
	update-rc.d -r ${D} mfg_test start 86 5 .

	install -d ${D}/${sysconfdir}/ssh
	install -m 644  ${S}/sshd_not_to_be_run ${D}/${sysconfdir}/ssh/
}
