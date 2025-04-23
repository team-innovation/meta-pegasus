SYSTEMD_SERVICE_${PN}-netd = "netd.service"
SYSTEMD_AUTO_ENABLE_${PN}-netd = "enable"

RDEPENDS_${PN}-netd = "\
    python3-pycryptodome \
    python3-pyserial \
    python3-ubus \
    python3-dbus \
    python3-avahi \
    python3-grpcio \
    python3-grpcio-tools \
    speedtest-cli \
	esp-idf \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/netd/
    ${RSYNC_CMD} ${S}/code/netd/ ${D}/${INSTALL_DIR}/netd/
    chmod +x ${D}/${INSTALL_DIR}/netd/netd.pyc

	install -d ${D}/usr/local/bin
	install ${S}/code/netd/scripts/speedtest ${D}/usr/local/bin
	chmod 0755 ${D}/usr/local/bin/*

	# systemd services
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/systemd/netd.service ${D}${systemd_unitdir}/system
}

FILES_${PN}-netd = "\
    ${INSTALL_DIR}/netd/* \
	${sysconfdir}/init.d/netd \
	${sysconfdir}/procman.d/netd \
    /usr/local/bin/speedtest \
	${systemd_unitdir}/system/netd.service \
"
