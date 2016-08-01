PKGR_${PN}-720 = "${PR}.2"

FIRMWARE_720 = "IP8337-VINT-0100d2.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_720};name=720"

SRC_URI[720.md5sum] = "bbe7fc756608ecf3d7ba36dfe11886f7"
SRC_URI[720.sha256sum] = "5b3d26026dbb0c3fa3d5341b9e7f000d0e22651d4445929a160b0bb4961a3abf"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_720} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-720 = "${FIRMWARE_DIR}/${FIRMWARE_720}"

            
