SYSTEMD_SERVICE_${PN}-345d = "345d.service"
SYSTEMD_AUTO_ENABLE_${PN}-345d = "enable"

RDEPENDS_${PN}-345d = "\
    python3-pyserial \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/345d/
	${RSYNC_CMD} ${S}/code/345d/ ${D}/${INSTALL_DIR}/345d/
	chmod +x ${D}/${INSTALL_DIR}/345d/345d.pyc

	# systemd services
    install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/systemd/345d.service ${D}${systemd_unitdir}/system
}

FILES_${PN}-345d = "\
	${INSTALL_DIR}/345d/* \
	${sysconfdir}/init.d/_345d \
	${sysconfdir}/procman.d/345d \
	${systemd_unitdir}/system/345d.service \
"
