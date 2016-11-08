PKGR_${PN}-721w = "${PR}.15"

FIRMWARE_721W = "IB8331W-VINT-0100b8.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_721W};name=721w"

SRC_URI[721w.md5sum] = "9550c29ea2d9e277f1bdb1f632081893"
SRC_URI[721w.sha256sum] = "ad8b9b51875c7bf475c6721fb31d6f81352f37e508a84df1b9f79afd13bd86f1"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_721W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-721w = "${FIRMWARE_DIR}/${FIRMWARE_721W}"

            
