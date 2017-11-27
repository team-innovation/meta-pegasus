PKGR_${PN}-520ir = "${PR}.47"

FIRMWARE_520IR = "IP8136W-ALAM-0100p2.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_520IR};name=520ir" 

SRC_URI[520ir.md5sum] = "edfbdb4fc3aee817e9f6f3982c5e896a"
SRC_URI[520ir.sha256sum] = "ceeb10d72948faf8aaf49afdbdf604ab7aa6aed5c3313570c3701ab371e4bf3d"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_520IR} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-520ir = "${FIRMWARE_DIR}/${FIRMWARE_520IR}"
FILES_${PN}-520ir-lic = "${FIRMWARE_DIR}/README"

