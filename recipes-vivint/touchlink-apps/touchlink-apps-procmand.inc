SYSTEMD_SERVICE_${PN}-procmand = "procmand.service"
SYSTEMD_AUTO_ENABLE_${PN}-procmand = "enable"

RDEPENDS_${PN}-procmand = "\
	bash \
	python3-pyinotify \
	python3-psutil \
 	python3-cherrypy	\
	${PN}-favicon \
	python3-jaracocollections \
    python3-jaracoclasses \
    python3-jaracofunctools \
    python3-jaracotempora \
    python3-jaracotext \
    python3-jinja2 \
"

RREPLACES_${PN}-procmand = "monit"
RCONFLICTS_${PN}-procmand = "monit"

INITSCRIPT_PACKAGES += "${PN}-procmand"
INITSCRIPT_NAME_${PN}-procmand = "procmand"
INITSCRIPT_PARAMS_${PN}-procmand = "defaults 88 18"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/procmand/
    ${RSYNC_CMD} ${S}/code/procmand/ ${D}/${INSTALL_DIR}/procmand/
    chmod +x ${D}/${INSTALL_DIR}/procmand/procmand.pyc

	# Install procman startup
	install -d ${D}/${sysconfdir}/procman.d/
	install ${WORKDIR}/procman.d/* ${D}/${sysconfdir}/procman.d/
	install -d ${D}/${sysconfdir}/procman.d-fcc/
	install ${WORKDIR}/procman.d-fcc/* ${D}/${sysconfdir}/procman.d-fcc/

	install -d ${D}/usr/local/bin
	install ${S}/code/procmand/scripts/procman ${D}/usr/local/bin
	chmod 0755 ${D}/usr/local/bin/*

	# systemd services
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/systemd/procmand.service ${D}${systemd_unitdir}/system
}

FILES_${PN}-procmand = "\
    ${INSTALL_DIR}/procmand/* \
	${sysconfdir}/init.d/procmand \
	${sysconfdir}/procman.d/syslogd \
	${systemd_unitdir}/system/procmand.service \
    /usr/local/bin/procman \
"
