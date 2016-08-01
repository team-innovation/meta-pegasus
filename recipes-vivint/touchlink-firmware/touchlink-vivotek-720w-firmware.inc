PKGR_${PN}-720w = "${PR}.5"

FIRMWARE_720W = "IP8333-VINT-0101c5.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_720W};name=720w"

SRC_URI[720w.md5sum] = "0d43ba807450adc02e18ed0eb6cf1a28"
SRC_URI[720w.sha256sum] = "7aa2498280df6e354ff41fc19fbfb148ad7db173afa52847d8c29ce7a27c108f"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_720W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-720w = "${FIRMWARE_DIR}/${FIRMWARE_720W}"

