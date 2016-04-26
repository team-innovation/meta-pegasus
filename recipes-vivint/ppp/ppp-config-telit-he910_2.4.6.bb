SECTION = "console/network"
DESCRIPTION = "Point-to-Point Protocol (PPP) configuration files for Telit HE910"
HOMEPAGE = "http://www.vivint.com/"
DEPENDS = "ppp"
LICENSE = "CLOSED"
PR = "r5"

RADIO_NAME = "Telit_he910"
SRC_URI = " \
	file://chatscripts_Telit_he910 \
	file://peers_Telit_he910 \
	file://peers_Telit_he910_serial \
	file://resolv_Telit_he910 \
	"

inherit autotools

do_compile() {
	:
}

do_runstrip() {
        :
}

do_install () {
	mkdir -p ${D}${sysconfdir}/chatscripts/
	mkdir -p ${D}${sysconfdir}/ppp/peers/
	mkdir -p ${D}${sysconfdir}/ppp/resolv/
 	install -m 0755 ${WORKDIR}/chatscripts_${RADIO_NAME} ${D}${sysconfdir}/chatscripts/${RADIO_NAME}
	install -m 0755 ${WORKDIR}/peers_${RADIO_NAME} ${D}${sysconfdir}/ppp/peers/${RADIO_NAME}
	install -m 0755 ${WORKDIR}/peers_${RADIO_NAME}_serial ${D}${sysconfdir}/ppp/peers/${RADIO_NAME}_serial
	install -m 0755 ${WORKDIR}/resolv_${RADIO_NAME}  ${D}${sysconfdir}/ppp/resolv/${RADIO_NAME}
}

PACKAGES = "${PN}"
FILES_${PN}        = "/etc"

