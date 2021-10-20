FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI_append += " \
	file://0001-Log-gst-messages-inside-of-qt.patch \
	file://0002-Add-level-plugin.patch \
	file://0003-Sync-sources-even-if-live.patch \
	file://0004-Add-latency-control.patch \
	file://0005-Fixes-1080p-clip-playback-on-imx6.patch \
	file://0087-Vivante-Keep-only-one-frame.patch \
	file://0091-GStreamer-Remove-dependency-to-gstreamer_imxcommon.patch \
"

PACKAGECONFIG[imx] = ",,gstreamer1.0-plugins-base"

do_install_append() {
if ls ${D}${libdir}/pkgconfig/Qt5*.pc >/dev/null 2>&1; then
    sed -i 's,-L${STAGING_DIR_HOST}/usr/lib,,' ${D}${libdir}/pkgconfig/Qt5*.pc
fi
}
