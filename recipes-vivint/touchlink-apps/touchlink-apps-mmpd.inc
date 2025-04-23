SYSTEMD_SERVICE_${PN}-mmpd = "mmpd.service"
SYSTEMD_AUTO_ENABLE_${PN}-mmpd = "enable"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/mmpd/
	${RSYNC_CMD} ${S}/code/mmpd/ ${D}/${INSTALL_DIR}/mmpd/
	chmod +x ${D}/${INSTALL_DIR}/mmpd/mmpd.pyc

	# systemd services
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/mmpd.service ${D}${systemd_unitdir}/system
}

FILES_${PN}-mmpd = "\
	${INSTALL_DIR}/mmpd/* \
	${sysconfdir}/init.d/mmpd \
	${sysconfdir}/procman.d/mmpd \
	${systemd_unitdir}/system/mmpd.service \
"
