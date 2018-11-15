SECTION = "console/network"
DESCRIPTION = "Point-to-Point Protocol (PPP) configuration files for Quectel EG91"
HOMEPAGE = "http://www.vivint.com/"
DEPENDS = "ppp"
LICENSE = "CLOSED"
PR = "r2"

RADIO_NAME = "Quectel_eg91"
SRC_URI = " \
	file://chatscripts_Quectel_eg91 \
	file://chatscripts_Quectel_eg91_vzn \
	file://peers_Quectel_eg91 \
	file://peers_Quectel_eg91_vzn \
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
 	install -m 0755 ${WORKDIR}/chatscripts_${RADIO_NAME} ${D}${sysconfdir}/chatscripts/${RADIO_NAME}
 	install -m 0755 ${WORKDIR}/chatscripts_${RADIO_NAME}_vzn ${D}${sysconfdir}/chatscripts/${RADIO_NAME}_vzn
	install -m 0755 ${WORKDIR}/peers_${RADIO_NAME} ${D}${sysconfdir}/ppp/peers/${RADIO_NAME}
	install -m 0755 ${WORKDIR}/peers_${RADIO_NAME}_vzn ${D}${sysconfdir}/ppp/peers/${RADIO_NAME}_vzn
}

PACKAGES = "${PN}"
FILES_${PN}        = "/etc"

