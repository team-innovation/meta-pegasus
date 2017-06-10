PKGR_${PN}-hd400w = "${PR}.7"

FIRMWARE_HD400W = "IB8363W-VINT-0300c.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_HD400W};name=hd400w"

SRC_URI[hd400w.md5sum] = "a48d53bb7755f6667bb070ce16b84018"
SRC_URI[hd400w.sha256sum] = "9116d0d726c3d00f8efe663448ae89d03be933ec1eec0d4b716be2157a4d4030"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_HD400W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-hd400w = "${FIRMWARE_DIR}/${FIRMWARE_HD400W}"

            
