SYSTEMD_SERVICE_${PN}-dbapd = "dbapd.service"
SYSTEMD_AUTO_ENABLE_${PN}-dbapd = "enable"

RDEPENDS_${PN}-dbapd = "\
    openssl \
    openssl-misc \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/dbapd/
	${RSYNC_CMD} ${S}/code/dbapd/ ${D}/${INSTALL_DIR}/dbapd/
	chmod +x ${D}/${INSTALL_DIR}/dbapd/dbapd.pyc

	# systemd services
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/systemd/dbapd.service ${D}${systemd_unitdir}/system
}

FILES_${PN}-dbapd = "\
	${INSTALL_DIR}/dbapd/* \
	${sysconfdir}/init.d/dbapd \
	${sysconfdir}/procman.d/dbapd \
	${systemd_unitdir}/system/dbapd.service \
"
