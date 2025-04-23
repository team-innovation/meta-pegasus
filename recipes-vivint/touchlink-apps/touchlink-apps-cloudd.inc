SYSTEMD_SERVICE_${PN}-cloudd = "cloudd.service"
SYSTEMD_AUTO_ENABLE_${PN}-cloudd = "enable"

RDEPENDS_${PN}-cloudd = "\
    python3-cachetools \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/cloudd/
	${RSYNC_CMD} ${S}/code/cloudd/ ${D}/${INSTALL_DIR}/cloudd/
	chmod +x ${D}/${INSTALL_DIR}/cloudd/cloudd.pyc

	# systemd services
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/cloudd.service ${D}${systemd_unitdir}/system
}

FILES_${PN}-cloudd = "\
	${INSTALL_DIR}/cloudd/* \
	${sysconfdir}/init.d/cloudd \
	${sysconfdir}/procman.d/cloudd \
	${systemd_unitdir}/system/cloudd.service \
"
