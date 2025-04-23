SYSTEMD_SERVICE_${PN}-videoclipd = "videoclipd.service"
SYSTEMD_AUTO_ENABLE_${PN}-videoclipd = "enable"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/videoclipd/
	${RSYNC_CMD} ${S}/code/videoclipd/ ${D}/${INSTALL_DIR}/videoclipd/
	chmod +x ${D}/${INSTALL_DIR}/videoclipd/videoclipd.pyc

	# systemd services
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/videoclipd.service ${D}${systemd_unitdir}/system
}


FILES_${PN}-videoclipd = "\
	${INSTALL_DIR}/videoclipd/* \
	${sysconfdir}/init.d/videoclipd \
	${sysconfdir}/procman.d/videoclipd \
	${systemd_unitdir}/system/videoclipd.service \
"
