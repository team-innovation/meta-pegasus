PKGR_${PN}-hd400w = "${PR}.1"

FIRMWARE_HD400W = "IB8363W-VINT-0100c.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_HD400W};name=hd400w"

SRC_URI[hd400w.md5sum] = "d4be48179dd2d5c11944ee89f32a3e24"
SRC_URI[hd400w.sha256sum] = "5cfdc2fa8a74b448af530d2773bc4049d773df3d97ebcfc3bdf802a0c20827aa"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_HD400W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-hd400w = "${FIRMWARE_DIR}/${FIRMWARE_HD400W}"

            
