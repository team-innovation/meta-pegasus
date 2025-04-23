SYSTEMD_SERVICE_${PN}-baguette = "baguette.service"
SYSTEMD_AUTO_ENABLE_${PN}-baguette = "enable"

REPLACES_${PN}-baguette = "${PN}-pumpernickel"

RDEPENDS_${PN}-baguette = "\
    python3-evdev \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/baguette/
    ${RSYNC_CMD} ${S}/code/baguette/ ${D}/${INSTALL_DIR}/baguette/
    chmod +x ${D}/${INSTALL_DIR}/baguette/baguette.pyc

	# systemd services
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/baguette.service ${D}${systemd_unitdir}/system
}

FILES_${PN}-baguette = "\
       ${INSTALL_DIR}/baguette/* \
	${sysconfdir}/init.d/baguette \
	${sysconfdir}/procman.d/baguette \
	${systemd_unitdir}/system/baguette.service \
"
