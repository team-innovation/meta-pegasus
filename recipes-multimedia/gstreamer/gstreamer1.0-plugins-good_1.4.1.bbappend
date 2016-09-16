PACKAGES += " gstreamer1.0-plugins-good-pulse"

RDEPENDS_gstreamer1.0-plugins-good-pulse += " tcp-wrappers"

FILESEXTRAPATHS_prepend := "${THISDIR}:"

SRC_URI += "file://dbc_clips_audio.patch"
