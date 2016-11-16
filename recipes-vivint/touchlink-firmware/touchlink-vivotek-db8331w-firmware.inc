PKGR_${PN}-db8331w = "${PR}.30"

FIRMWARE_DB8331W = "DB8331W-VINT-0101r.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8331W};name=db8331w"

SRC_URI[db8331w.md5sum] = "d797cc1e4095d841cabbd39aed47cb83"
SRC_URI[db8331w.sha256sum] = "10f7fdd562c75ddb9504e9b72137204b44fa2fc5f7321fe03a80f7fffffe49cb"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8331W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8331w = "${FIRMWARE_DIR}/${FIRMWARE_DB8331W}"

