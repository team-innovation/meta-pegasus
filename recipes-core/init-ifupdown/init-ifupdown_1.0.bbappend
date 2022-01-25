# Look for file in this layer first
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
PR_append = "+viv8"
SRC_URI += "file://interfaces.ngh \
	    file://add_default_lo_route"

do_install_append() {
	install -m 0644 ${WORKDIR}/interfaces.ngh ${D}${sysconfdir}/network/interfaces
	install -d ${D}${sysconfdir}/network/if-up.d
	install -m 0755 ${WORKDIR}/add_default_lo_route ${D}${sysconfdir}/network/if-up.d
}

FILES_${PN} += "${sysconfdir}/network/if-up.d/add_default_lo_route"
