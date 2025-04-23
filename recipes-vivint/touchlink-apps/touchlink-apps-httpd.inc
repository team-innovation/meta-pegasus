SYSTEMD_SERVICE_${PN}-httpd = "httpd.service"
SYSTEMD_AUTO_ENABLE_${PN}-httpd = "enable"

RDEPENDS_${PN}-httpd = "\
    python3-zeroconf \
    python3-sseclient \
    python3-ifaddr \
    python3-pyyaml \
    python3-jacobs-json-schema \
    python3-aiohttp \
    python3-aiohttp-security \
    python3-aiohttp-session \
    python3-aiohttp-sse \
    python3-idna \
    python3-idna-ssl \
    python3-pyjwt \
    python3-aioconsole \
    dect-python-cmnd \    
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/httpd/
    ${RSYNC_CMD} ${S}/code/httpd/ ${D}/${INSTALL_DIR}/httpd/
    chmod +x ${D}/${INSTALL_DIR}/httpd/httpd.pyc

	# systemd services
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/httpd.service ${D}${systemd_unitdir}/system
}

FILES_${PN}-httpd = "\
	${INSTALL_DIR}/httpd/*   \
	${sysconfdir}/init.d/httpd \
	${sysconfdir}/procman.d/httpd \
	${systemd_unitdir}/system/httpd.service \
"
