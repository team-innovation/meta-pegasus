FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "file://chrony_start.sh \
	file://chrony_stop.sh \
	file://init \
	file://chrony.conf.slimline \
    file://chrony.conf.sly \
    file://chrony.conf.wallsly \
"

DEPENDS += "readline libcap update-rc.d-native"
RDEPENDS_${PN} += "readline bash libcap"

inherit autotools-brokensep update-rc.d

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0644 ${WORKDIR}/chrony.conf.sly ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/chrony.conf.slimline ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/chrony.conf.wallsly ${D}${sysconfdir}
    install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/chronyd
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/chrony_start.sh ${D}${bindir}
    install -m 0755 ${WORKDIR}/chrony_stop.sh ${D}${bindir}

    update-rc.d -r ${D} chronyd defaults 85
}

FILES_${PN} += "/usr/bin/chrony_stop.sh /usr/bin/chrony_start.sh"
