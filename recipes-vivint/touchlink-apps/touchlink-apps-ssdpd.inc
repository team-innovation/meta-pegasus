SYSTEMD_SERVICE_${PN}-ssdpd = "ssdpd.service"
SYSTEMD_AUTO_ENABLE_${PN}-ssdpd = "enable"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/ssdpd/
	${RSYNC_CMD} ${S}/code/ssdpd/ ${D}/${INSTALL_DIR}/ssdpd/
	chmod +x ${D}/${INSTALL_DIR}/ssdpd/ssdpd.pyc

	# systemd services
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/systemd/ssdpd.service ${D}${systemd_unitdir}/system
}

FILES_${PN}-ssdpd = "\
	${INSTALL_DIR}/ssdpd/* \
	${sysconfdir}/init.d/ssdpd \
	${sysconfdir}/procman.d/ssdpd \
	${systemd_unitdir}/system/ssdpd.service \
"
