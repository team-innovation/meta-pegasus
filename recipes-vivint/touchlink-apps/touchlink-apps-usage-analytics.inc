SYSTEMD_SERVICE_${PN}-usage-analytics = "usage-analytics.service"
SYSTEMD_AUTO_ENABLE_${PN}-usage-analytics = "enable"

RDEPENDS_${PN}-usage-analytics = "\
    python3-mixpanel \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/usage_analytics/
	${RSYNC_CMD} ${S}/code/usage_analytics/ ${D}/${INSTALL_DIR}/usage_analytics/
	chmod +x ${D}/${INSTALL_DIR}/usage_analytics/usage_analytics.pyc

	# systemd services
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/systemd/usage-analytics.service ${D}${systemd_unitdir}/system
}

FILES_${PN}-usage-analytics = "\
	${INSTALL_DIR}/usage_analytics/* \
	${sysconfdir}/init.d/usage_analytics \
	${sysconfdir}/procman.d/usage_analytics \
	${systemd_unitdir}/system/usage-analytics.service \
"
