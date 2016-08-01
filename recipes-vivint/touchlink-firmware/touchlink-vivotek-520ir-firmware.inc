PKGR_${PN}-520ir = "${PR}.45"

FIRMWARE_520IR = "IP8136W-ALAM-0100o9.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_520IR};name=520ir" 

SRC_URI[520ir.md5sum] = "b3928833a2cd830c7e9f5bb6cea68d9b"
SRC_URI[520ir.sha256sum] = "d615fa908db9afbabdac5f3c4dd5ee08e44fc0224a8ea2ea60378a39c03bdf8e"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_520IR} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-520ir = "${FIRMWARE_DIR}/${FIRMWARE_520IR}"
FILES_${PN}-520ir-lic = "${FIRMWARE_DIR}/README"

