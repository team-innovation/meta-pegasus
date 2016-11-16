PKGR_${PN}-721w = "${PR}.16"

FIRMWARE_721W = "IB8331W-VINT-0100b9.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_721W};name=721w"

SRC_URI[721w.md5sum] = "7841963d74ef0f81f2cfd935d1a1564c"
SRC_URI[721w.sha256sum] = "c8423809996b1372a8690b197e8560428dbf330d6b810682a568a7039b1588b3"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_721W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-721w = "${FIRMWARE_DIR}/${FIRMWARE_721W}"

            
