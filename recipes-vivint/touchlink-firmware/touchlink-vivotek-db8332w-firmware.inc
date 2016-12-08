PKGR_${PN}-db8332w = "${PR}.19"

FIRMWARE_DB8332W = "DB8332W-VINT-0101a.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8332W};name=db8332w"

SRC_URI[db8332w.md5sum] = "f06723e20f972ee286d8b9d3112af183"
SRC_URI[db8332w.sha256sum] = "b67b887553ee483fa4d1377385b76189d9be5a9759806d168bbf62bd0cdcd6e1"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8332W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8332w = "${FIRMWARE_DIR}/${FIRMWARE_DB8332W}"
