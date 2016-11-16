PKGR_${PN}-hd400w = "${PR}.3"

FIRMWARE_HD400W = "IB8363W-VINT-0100e.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_HD400W};name=hd400w"

SRC_URI[hd400w.md5sum] = "406d08fdc195f39725899e807f8167da"
SRC_URI[hd400w.sha256sum] = "4af340c1cf2e906f99fd0c462ae9738624006ca8f85c036b13a1e1cf327dd7e8"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_HD400W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-hd400w = "${FIRMWARE_DIR}/${FIRMWARE_HD400W}"

            
