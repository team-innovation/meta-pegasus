PKGR_${PN}-721w = "${PR}.13"

FIRMWARE_721W = "IB8331W-VINT-0100b6.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_721W};name=721w"

SRC_URI[721w.md5sum] = "dd8e66113b374a97202cbc4ba0866696"
SRC_URI[721w.sha256sum] = "ff4fe07a48c636a95971d984e5baf53d4df026ae67be11fc237135d8730759d1"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_721W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-721w = "${FIRMWARE_DIR}/${FIRMWARE_721W}"

            
