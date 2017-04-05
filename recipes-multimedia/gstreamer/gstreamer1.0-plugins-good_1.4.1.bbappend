PACKAGES += " gstreamer1.0-plugins-good-pulse"

RDEPENDS_gstreamer1.0-plugins-good-pulse += " tcp-wrappers"

PR = "r6"

FILESEXTRAPATHS_prepend := "${THISDIR}:"

SRC_URI += "file://dbc_clips_audio.patch"
SRC_URI += "file://dbc_clips_seek_audio.patch"
SRC_URI += "file://gst_rtspsrc_close_tear_down.patch"
