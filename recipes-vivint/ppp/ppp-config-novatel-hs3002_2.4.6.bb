SECTION = "console/network"
DESCRIPTION = "Point-to-Point Protocol (PPP) configuration files for Novatel CDMA"
HOMEPAGE = "http://www.vivint.com/"
DEPENDS = "ppp"
LICENSE = "CLOSED"
PR = "r3"

RADIO_NAME = "Novatel_hs3002"
SRC_URI = " \
	file://chatscripts_Novatel_hs3002 \
	file://peers_Novatel_hs3002 \
	file://peers_Novatel_hs3002_serial \
	file://resolv_Novatel_hs3002 \
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
	install -m 0755 ${WORKDIR}/resolv_${RADIO_NAME} ${D}${sysconfdir}/ppp/resolv/${RADIO_NAME}
}

PACKAGES = "${PN}"
FILES_${PN}        = "/etc"

