PKGR_${PN}-hd400w = "${PR}.11"

FIRMWARE_HD400W = "IB8363W-VINT-0300i.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_HD400W};name=hd400w"

SRC_URI[hd400w.md5sum] = "1c238d1dde5b50b062d88e60209edb4e"
SRC_URI[hd400w.sha256sum] = "a1e55807daee519f1d1183e699270f1f338520acf738aefef8d6540802c7066a"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_HD400W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-hd400w = "${FIRMWARE_DIR}/${FIRMWARE_HD400W}"

            
