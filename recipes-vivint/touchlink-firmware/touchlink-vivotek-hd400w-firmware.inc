PKGR_${PN}-hd400w = "${PR}.8"

FIRMWARE_HD400W = "IB8363W-VINT-0300d.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_HD400W};name=hd400w"

SRC_URI[hd400w.md5sum] = "cde7738cd165758dd24764ab50e04cfc"
SRC_URI[hd400w.sha256sum] = "b6c34b2db463f0335e658ee49961ee2ec033b23782c0e2b276cd5ec0fa8fb1fa"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_HD400W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-hd400w = "${FIRMWARE_DIR}/${FIRMWARE_HD400W}"

            
