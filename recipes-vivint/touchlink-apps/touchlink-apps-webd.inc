SYSTEMD_SERVICE_${PN}-webd = "webd.socket"
SYSTEMD_AUTO_ENABLE_${PN}-webd = "disable"

RDEPENDS_${PN}-webd = "\
	python3-cherrypy \
	python3-jaracocollections \
    python3-jaracoclasses \
    python3-jaracofunctools \
    python3-jaracotempora \
    python3-jaracotext \
"

do_install_append () {
    install -d ${D}/${INSTALL_DIR}/webd/
    ${RSYNC_CMD} ${S}/code/webd/ ${D}/${INSTALL_DIR}/webd/
    chmod +x ${D}/${INSTALL_DIR}/webd/webd.pyc
	# systemd services
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/webd.service ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/webd.socket ${D}${systemd_unitdir}/system
}

FILES_${PN}-webd = "\
    ${INSTALL_DIR}/webd/* \
	${sysconfdir}/init.d/webd \
    ${systemd_unitdir}/system/webd.service \
    ${systemd_unitdir}/system/webd.socket \
"
