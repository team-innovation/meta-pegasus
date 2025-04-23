SYSTEMD_SERVICE_${PN}-modemd = "modemd.service"
SYSTEMD_AUTO_ENABLE_${PN}-modemd = "enable"

RDEPENDS_${PN}-modemd = "\
    python3-pyserial \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/modemd/
	${RSYNC_CMD} ${S}/code/modemd/ ${D}/${INSTALL_DIR}/modemd/
	chmod +x ${D}/${INSTALL_DIR}/modemd/modemd.pyc

	# systemd services
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/modemd.service ${D}${systemd_unitdir}/system
}

FILES_${PN}-modemd-proxies = "\
    ${INSTALL_DIR}/modemd/proxies/* \
"

FILES_${PN}-modemd = "\
	${INSTALL_DIR}/modemd/*	\
	${sysconfdir}/init.d/modemd \
	${sysconfdir}/procman.d/modemd \
	${systemd_unitdir}/system/modemd.service \
"
