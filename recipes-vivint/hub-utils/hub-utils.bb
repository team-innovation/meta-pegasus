# Copyright (C) 2016 Vivint Innovation

DESCRIPTION = "Vivint Hub Utilities"
SECTION = "Hub"
LICENSE = "CLOSED"
DEPENDS = ""
# PV = "${DISTRO_VERSION}"
PV = "1.0.0"
PR = "r2"

SRC_URI = "\
	file://mfg_test \
	file://sshd_not_to_be_run \
	file://udhcpd.conf \
	file://udhcpd.leases \
"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"
S = "${WORKDIR}"

RPROVIDES_${PN} = "hub-utils"

inherit update-rc.d

INITSCRIPT_NAME = "mfg_test"
INITSCRIPT_PARAMS = "start 86 5 ."

do_install() {
	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${S}/mfg_test ${D}/${sysconfdir}/init.d/mfg_test

	install -d ${D}/${sysconfdir}/ssh
	install -m 644  ${S}/sshd_not_to_be_run ${D}/${sysconfdir}/ssh/

	install -m 0644 ${S}/udhcpd.conf ${D}${sysconfdir}
	install -d ${D}/var/lib/misc
	install -m 0644 ${S}/udhcpd.leases ${D}/var/lib/misc
}

RREPLACES_${PN} = "sly-utils"
RCONFLICTS_${PN} = "sly-utils"

