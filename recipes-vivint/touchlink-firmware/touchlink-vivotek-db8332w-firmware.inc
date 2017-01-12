PKGR_${PN}-db8332w = "${PR}.20"

FIRMWARE_DB8332W = "DB8332W-VINT-0101b.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8332W};name=db8332w"

SRC_URI[db8332w.md5sum] = "634adbb8221509716a2a264f55351c43"
SRC_URI[db8332w.sha256sum] = "5c61bab49c4a0435b61ed34894c979234b17f5dcfce3ed4db6951691736dc4f6"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8332W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8332w = "${FIRMWARE_DIR}/${FIRMWARE_DB8332W}"
