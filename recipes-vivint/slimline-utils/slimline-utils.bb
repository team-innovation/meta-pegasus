# Copyright (C) 2017 Vivint Innovation 

DESCRIPTION = "Glance Utilities"
SECTION = "glance"
LICENSE = "CLOSED"
DEPENDS = ""
# PV = "${DISTRO_VERSION}"
PV = "1.0.0"
PR = "r6"

SRC_URI = "\
	file://mfg_test \
    file://connect_to_ap.py \
    file://reset_to_factory_defaults.py \
    file://udhcpd.conf \
    file://udhcpd.leases \
"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"
S = "${WORKDIR}"

RPROVIDES_${PN} = "slimline-utils"

do_install() {
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${S}/mfg_test ${D}/${sysconfdir}/init.d/mfg_test
    install -d ${D}/srv/www/cgi-bin
    install -m 0700 ${S}/connect_to_ap.py ${D}/srv/www/cgi-bin
    install -m 0700 ${S}/reset_to_factory_defaults.py ${D}/srv/www/cgi-bin
    install -m 0644 ${S}/udhcpd.conf ${D}${sysconfdir}
    install -d ${D}/var/lib/misc
    install -m 0644 ${S}/udhcpd.leases ${D}/var/lib/misc
    update-rc.d -r ${D} mfg_test start 86 5 . 
}

FILES_${PN} += "${sysconfdir}/udhcpd.conf /var/lib/misc/udhcpd.leases /srv/www/cgi-bin/*"
