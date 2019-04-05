PKGR_${PN}-db8332w = "${PR}.29"

FIRMWARE_DB8332W = "DB8332W-VINT-0200m.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8332W};name=db8332w"

SRC_URI[db8332w.md5sum] = "a0fef83228ae6f7ea957e0ce8694b7b4"
SRC_URI[db8332w.sha256sum] = "ee4c23836289a2d590344cc0e5e2994f13997ff446432847d63a6e593d2c8357"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8332W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8332w = "${FIRMWARE_DIR}/${FIRMWARE_DB8332W}"
