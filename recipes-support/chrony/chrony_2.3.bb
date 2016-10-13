SUMMARY = "Chrony NTP server/client"
HOMEPAGE = "https://chrony.tuxfamily.org/index.html"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

PR = "r4"

SRC_URI = "https://download.tuxfamily.org/chrony/${BPN}-${PV}.tar.gz \
	file://chrony_start.sh \
	file://chrony_stop.sh \
	file://init \
	file://chrony.conf.slimline \
    file://chrony.conf.sly \
"

SRC_URI[md5sum] = "db6d46afea66f75dcc362f44623c1af4"
SRC_URI[sha256sum] = "58bffb523012fb0fa87cc0d94d6e36de9689fe9556519cbd0d1ba254af92ccb2"

DEPENDS += "readline libcap"
REDEPENDS += "readline libcap"

inherit autotools-brokensep

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0644 ${WORKDIR}/chrony.conf.sly ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/chrony.conf.slimline ${D}${sysconfdir}
    install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/chronyd
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/chrony_start.sh ${D}${bindir}
    install -m 0755 ${WORKDIR}/chrony_stop.sh ${D}${bindir}

    update-rc.d -r ${D} chronyd defaults 85
}
