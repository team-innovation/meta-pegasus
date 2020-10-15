FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

DEPENDS_append = " virtual/kernel"

PR = "1"
SRC_URI += "file://use_revb.patch"

# CONFIG_LOADADDR is needed for signing the kernel image
do_deploy_append() {
	install -d ${DEPLOYDIR}/${BOOT_TOOLS}
	install -m 0655 ${B}/${config}/u-boot.cfg  ${DEPLOYDIR}/${BOOT_TOOLS}
}
