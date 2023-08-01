FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"


SRC_URI = " \
    ${QT_GIT}/${QT_MODULE}.git;name=${QT_MODULE};${QT_MODULE_BRANCH_PARAM};protocol=${QT_GIT_PROTOCOL} \
    file://0001-qtmultimedia-fix-a-conflicting-declaration.patch \
"


SRC_URI_append += " \
	file://0001-Log-gst-messages-inside-of-qt.patch \
	file://0002-Add-level-plugin.patch \
	file://0003-Sync-sources-even-if-live.patch \
	file://0004-Add-latency-control.patch \
	file://0005-Fixes-1080p-clip-playback-on-imx6.patch \
	file://discard-bad-frames.patch \
"

SRCREV = "baeb08ba3ab23b2266d1bad45bb7a2769b841c2e"

PACKAGECONFIG[imx] = ",,gstreamer1.0-plugins-base"
