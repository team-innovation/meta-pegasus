PKGR_${PN}-721w = "${PR}.17"

FIRMWARE_721W = "IB8331W-VINT-0100c1.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_721W};name=721w"

SRC_URI[721w.md5sum] = "a37893fec7ab373ed30ef07d9474a469"
SRC_URI[721w.sha256sum] = "f7bc90e16d666b041538ff83e76181c6eea305eb541a825d17b758d321468d06"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_721W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-721w = "${FIRMWARE_DIR}/${FIRMWARE_721W}"

            
