PKGR_${PN}-hd400w = "${PR}.4"

FIRMWARE_HD400W = "IB8363W-VINT-0100g.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_HD400W};name=hd400w"

SRC_URI[hd400w.md5sum] = "8bf4050f5df196027b639b8f4c3d821d"
SRC_URI[hd400w.sha256sum] = "501e0387f966f42506e3a6703df229530fae03d5808ff500976c644c8e2b74a4"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_HD400W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-hd400w = "${FIRMWARE_DIR}/${FIRMWARE_HD400W}"

            
