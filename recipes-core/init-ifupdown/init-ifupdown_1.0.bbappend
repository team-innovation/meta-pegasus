# Look for file in this layer first
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
PR_append = "+viv2"
SRC_URI += "file://interfaces.ngh"

do_install_append() {
	install -m 0644 ${WORKDIR}/interfaces.ngh ${D}${sysconfdir}/network/interfaces
}
