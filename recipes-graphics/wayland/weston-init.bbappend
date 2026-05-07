FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR="r7"

SRC_URI += "file://logrotate \
            file://weston.ini \
            file://weston.ini_pegasus \
"

BUILD_PLATFORM = "other"
BUILD_PLATFORM:imx8mm-yellowstone = "yellowstone"
BUILD_PLATFORM:imx8mn-pegasus = "pegasus"

do_install:append(){
    install -d ${D}${sysconfdir}/logrotate.d
    install -m 0600 ${WORKDIR}/logrotate ${D}${sysconfdir}/logrotate.d/weston
    install -d ${D}/etc/xdg/weston
	install -m 0755 ${WORKDIR}/weston.ini_pegasus ${D}/etc/xdg/weston/weston.ini
}
