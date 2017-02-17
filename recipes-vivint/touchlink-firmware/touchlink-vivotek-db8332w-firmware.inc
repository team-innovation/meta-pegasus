PKGR_${PN}-db8332w = "${PR}.21"

FIRMWARE_DB8332W = "DB8332W-VINT-0101c.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8332W};name=db8332w"

SRC_URI[db8332w.md5sum] = "1585ce3454aafa19798a6397259d7da4"
SRC_URI[db8332w.sha256sum] = "89b0866317ba59313e284fdd19fab4b045674c128a978f16da9b0be6f8ba444f"
 
do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8332W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8332w = "${FIRMWARE_DIR}/${FIRMWARE_DB8332W}"
