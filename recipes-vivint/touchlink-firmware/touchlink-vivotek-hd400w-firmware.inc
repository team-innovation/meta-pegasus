PKGR_${PN}-hd400w = "${PR}.2"

FIRMWARE_HD400W = "IB8363W-VINT-0100d.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_HD400W};name=hd400w"

SRC_URI[hd400w.md5sum] = "347f5ccf4fca52f64c8684d18dd9f81c"
SRC_URI[hd400w.sha256sum] = "dde54c6515c2b400582cac632c97219be6a8e53cc29f7266d8b7f9c90b90b6b9"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_HD400W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-hd400w = "${FIRMWARE_DIR}/${FIRMWARE_HD400W}"

            
