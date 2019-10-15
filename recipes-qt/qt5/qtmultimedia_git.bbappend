FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR = "r3"

SRC_URI += "file://qtmultimedia_levels.patch \
	file://enable-live-sync.patch \
	file://qtlatency.patch \
	file://imxdmabuf.patch \
	"
