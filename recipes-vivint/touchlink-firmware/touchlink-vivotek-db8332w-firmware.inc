PKGR_${PN}-db8332w = "${PR}.25"

FIRMWARE_DB8332W = "DB8332W-VINT-0200h.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8332W};name=db8332w"

SRC_URI[db8332w.md5sum] = "e1bbcc6c98674d05bc3c386156529b48"
SRC_URI[db8332w.sha256sum] = "3f5b6724ca7c230b9ae372663f8e1a7c656944b6e3743db534804bbb53c16abc"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8332W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8332w = "${FIRMWARE_DIR}/${FIRMWARE_DB8332W}"
