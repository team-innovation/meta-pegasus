SYSTEMD_SERVICE_${PN}-videod = "videod.service"
SYSTEMD_AUTO_ENABLE_${PN}-videod = "enable"

RDEPENDS_${PN}-videod = "\
    python3-live555proxy \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/videod/
	${RSYNC_CMD} ${S}/code/videod/ ${D}/${INSTALL_DIR}/videod/
	chmod +x ${D}/${INSTALL_DIR}/videod/videod.pyc

	# systemd services
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/videod.service ${D}${systemd_unitdir}/system
}


FILES_${PN}-videod = "\
	${INSTALL_DIR}/videod/* \
	${sysconfdir}/init.d/videod \
	${sysconfdir}/procman.d/videod \
	${systemd_unitdir}/system/videod.service \
"
