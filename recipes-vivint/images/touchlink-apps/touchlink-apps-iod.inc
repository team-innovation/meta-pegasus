SYSTEMD_SERVICE_${PN}-iod = "iod.service"
SYSTEMD_AUTO_ENABLE_${PN}-iod = "enable"

RDEPENDS_${PN}-iod += "\
    bash \
    python3-pytz \
    python3-pexpect \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/iod/
    ${RSYNC_CMD} ${S}/code/iod/ ${D}/${INSTALL_DIR}/iod/
    chmod +x ${D}/${INSTALL_DIR}/iod/iod.pyc

	# systemd services
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/iod.service ${D}${systemd_unitdir}/system
}

FILES_${PN}-iod = "\
    ${INSTALL_DIR}/iod/*   \
	${sysconfdir}/init.d/iod		\
	${sysconfdir}/procman.d/iod		\
    ${systemd_unitdir}/system/iod.service \
"
