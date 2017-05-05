PKGR_${PN}-hd400w = "${PR}.6"

FIRMWARE_HD400W = "IB8363W-VINT-0300b.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_HD400W};name=hd400w"

SRC_URI[hd400w.md5sum] = "951549803fd4bd0428863e85c2ef9377"
SRC_URI[hd400w.sha256sum] = "a019d8f9e1aad337281595e6a6eeff3e46e2bb1a4e5dd2b9d77133ed6c61fdca"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_HD400W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-hd400w = "${FIRMWARE_DIR}/${FIRMWARE_HD400W}"

            
