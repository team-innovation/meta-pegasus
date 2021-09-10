# Look for file in this layer first
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"
PR_append = "+viv2"
SRC_URI += "file://interfaces.slimline \
	    file://networking_vivint"

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/networking_vivint ${D}${sysconfdir}/init.d/networking
	install -m 0644 ${WORKDIR}/interfaces.slimline ${D}${sysconfdir}/network/interfaces
}
