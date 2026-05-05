FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR="r6"

SRC_URI += "file://logrotate \
            file://weston.ini \
"

BUILD_PLATFORM = "other"
BUILD_PLATFORM:imx8mm-yellowstone = "yellowstone"

do_install:append(){
    install -d ${D}${sysconfdir}/logrotate.d
    install -m 0600 ${WORKDIR}/logrotate ${D}${sysconfdir}/logrotate.d/weston
    install -d ${D}/etc/xdg/weston
	install -m 0755 ${WORKDIR}/weston.ini ${D}/etc/xdg/weston
}
