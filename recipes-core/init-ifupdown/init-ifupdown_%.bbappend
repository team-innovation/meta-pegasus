# Look for file in this layer first
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR_append = "+viv1"
SRC_URI += "file://interfaces.ngh \
	    file://interfaces.slimline"

do_install_append() {
	if [ ${HOST_SYS} != "aarch64-poky-linux" ]; then
		install -m 0644 ${WORKDIR}/interfaces.slimline ${D}${sysconfdir}/network/interfaces
	else
		install -m 0644 ${WORKDIR}/interfaces.ngh ${D}${sysconfdir}/network/interfaces
	fi
}
