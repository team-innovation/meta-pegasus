PKGR_${PN}-721w = "${PR}.14"

FIRMWARE_721W = "IB8331W-VINT-0100b7.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_721W};name=721w"

SRC_URI[721w.md5sum] = "c9bfb00cdfa767ff44cad3d961f549a6"
SRC_URI[721w.sha256sum] = "c8ae0ed3fe9b9bc85509e4ee28758ac895cd19c8be602e22634933ad3b4f0fab"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_721W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-721w = "${FIRMWARE_DIR}/${FIRMWARE_721W}"

            
