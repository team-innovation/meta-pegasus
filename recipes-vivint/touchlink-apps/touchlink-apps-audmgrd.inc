SYSTEMD_SERVICE_${PN}-audmgrd = "audmgrd.service"
SYSTEMD_AUTO_ENABLE_${PN}-audmgrd = "enable"

RDEPENDS_${PN}-audmgrd = "\
	python3-audio \
	python3-pyalsaaudio \
    python3-pulseaudio \
    python3-mmap \
    python3-pexpect \
	gstreamer1.0 \
	gstreamer1.0-plugins-bad \
	gstreamer1.0-plugins-base \
	gstreamer1.0-plugins-good \    
"
do_compile_prepend() {
    # compile dbc audio C code
    oe_runmake -C ${S}/code/audmgrd/libaudio SYSTEM="NEXT_GEN_HUB"
    oe_runmake -C ${S}/code/audmgrd/audio_sync
}

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/audmgrd/
    ${RSYNC_CMD} --exclude audio_sync ${S}/code/audmgrd/ ${D}/${INSTALL_DIR}/audmgrd/
    chmod +x ${D}/${INSTALL_DIR}/audmgrd/audmgrd.pyc

    install -d ${D}/usr/local/bin
    install -m 0755 ${S}/code/audmgrd/audio_sync/gst_client ${D}/usr/local/bin
    install -m 0755 ${S}/code/audmgrd/audio_sync/gst_server ${D}/usr/local/bin

	# systemd services
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/audmgrd.service ${D}${systemd_unitdir}/system
}

FILES_${PN}-audmgrd = "\
    ${INSTALL_DIR}/audmgrd/* \
	${sysconfdir}/init.d/audmgrd \
	${sysconfdir}/procman.d/audmgrd \
    ${systemd_unitdir}/system/audmgrd.service \
    /usr/local/bin/gst_* \
"
