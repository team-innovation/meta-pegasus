SYSTEMD_SERVICE:${PN}-iod = "iod.service"
SYSTEMD_AUTO_ENABLE:${PN}-iod = "enable"

SYSTEMD_PACKAGES += "${PN}-iod"

RDEPENDS:${PN}-iod += "\
    bash \
    python3-pytz \
    python3-pexpect \
"

do_install:append () {
	install -d ${D}/${INSTALL_DIR}/iod/
    ${RSYNC_CMD} ${S}/code/iod/ ${D}/${INSTALL_DIR}/iod/

	# systemd services
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/iod.service ${D}${systemd_unitdir}/system
}

FILES:${PN}-iod = "\
    ${INSTALL_DIR}/iod/* \
	${sysconfdir}/procman.d/iod \
    ${systemd_unitdir}/system/iod.service \
"
