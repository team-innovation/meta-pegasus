# Look for file in this layer first
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI += "file://interfaces.slimline"

do_install_append() {
	install -m 0644 ${WORKDIR}/interfaces.slimline ${D}${sysconfdir}/network/interfaces
}
