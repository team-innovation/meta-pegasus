FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR="r6"

SRC_URI += "file://logrotate \
            file://weston.ini \
            file://weston.ini_yellowstone \
            file://weston.service \
"

#BUILD_PLATFORM = "other"
#BUILD_PLATFORM_imx8mm-yellowstone = "yellowstone"

do_install:yellowstone:append() {
    install -m 0755 ${WORKDIR}/weston.ini_yellowstone ${D}/etc/xdg/weston/weston.ini
}

do_install:append(){
    install -d ${D}${sysconfdir}/logrotate.d
    install -m 0600 ${WORKDIR}/logrotate ${D}${sysconfdir}/logrotate.d/weston
    install -d ${D}/etc/xdg/weston

    # default ini
    install -m 0755 ${WORKDIR}/weston.ini ${D}/etc/xdg/weston

    # override systemd unit
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/weston.service ${D}${systemd_unitdir}/system/weston@.service
}

FILES:${PN} += "${sysconfdir}/logrotate.d/weston"
