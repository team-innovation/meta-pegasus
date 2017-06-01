PKGR_${PN}-db8332w = "${PR}.23"

FIRMWARE_DB8332W = "DB8332W-VINT-0200c.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8332W};name=db8332w"

SRC_URI[db8332w.md5sum] = "5925cf58c59b4cb64bbd3798c44aa186"
SRC_URI[db8332w.sha256sum] = "0c14ce740e7417e76eeaf630ea225e637ea75dd6a41229f5f8c75c6add2d5a1c" 

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8332W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8332w = "${FIRMWARE_DIR}/${FIRMWARE_DB8332W}"
