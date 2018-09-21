PKGR_${PN}-hd400w = "${PR}.9"

FIRMWARE_HD400W = "IB8363W-VINT-0300h.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_HD400W};name=hd400w"

SRC_URI[hd400w.md5sum] = "092849913d77789dd440a5bcf08edabe"
SRC_URI[hd400w.sha256sum] = "73a8b968f58297aafb468000b2fa949051891687de50c3993bcbd51476c9b7f5"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_HD400W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-hd400w = "${FIRMWARE_DIR}/${FIRMWARE_HD400W}"

            
