FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR = "r3"
PACKAGECONFIG[gstreamer_allocator] = "-gstreamer_allocator,,gst-plugins-base"
PACKAGECONFIG += " gstreamer_allocator alsa pulseaudio"

SRC_URI += "file://qtmultimedia_levels.patch \
	file://enable-live-sync.patch \
	file://qtlatency.patch \
	file://imxdmabuf.patch \
	"
