FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI_append += " \
	file://0087-Vivante-Keep-only-one-frame.patch \
	file://0091-GStreamer-Remove-dependency-to-gstreamer_imxcommon.patch \
"

PACKAGECONFIG[imx] = ",,gstreamer1.0-plugins-base"
