PKGR_${PN}-720 = "${PR}.3"

FIRMWARE_720 = "IP8337-VINT-0100d3.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_720};name=720"

SRC_URI[720.md5sum] = "3cd73cf2d03791723d47b567b354c895"
SRC_URI[720.sha256sum] = "1d05e34553984624144b09309a7e65a00f994add3e040cbd9ed2fa72d3e55621"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_720} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-720 = "${FIRMWARE_DIR}/${FIRMWARE_720}"

            
