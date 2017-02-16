PKGR_${PN}-hd400w = "${PR}.5"

FIRMWARE_HD400W = "IB8363W-VINT-0100i.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_HD400W};name=hd400w"

SRC_URI[hd400w.md5sum] = "45f5f967760c67baf7c77eec9bf9153b"
SRC_URI[hd400w.sha256sum] = "01c5e60a64fb75c691583d6c9b418d51b4d0dab5bd26550bc89dc9e99cfb08fc"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_HD400W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-hd400w = "${FIRMWARE_DIR}/${FIRMWARE_HD400W}"

            
