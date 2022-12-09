FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI_append += " \
	file://0001-Log-gst-messages-inside-of-qt.patch \
	file://0002-Add-level-plugin.patch \
	file://0003-Sync-sources-even-if-live.patch \
	file://0004-Add-latency-control.patch \
	file://0005-Fixes-1080p-clip-playback-on-imx6.patch \
	file://0087-Vivante-Keep-only-one-frame.patch \
    file://discard-bad-frames.patch \
	file://0091-GStreamer-Remove-dependency-to-gstreamer_imxcommon.patch \
"

PACKAGECONFIG[imx] = ",,gstreamer1.0-plugins-base"
