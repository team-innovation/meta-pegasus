PKGR_${PN}-db8331w = "${PR}.29"

FIRMWARE_DB8331W = "DB8331W-VINT-0101q.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8331W};name=db8331w"

SRC_URI[db8331w.md5sum] = "55d47f1458ace525fbf0f29cb580200c"
SRC_URI[db8331w.sha256sum] = "aefaf77c1dcd50e9693fd98fc5a9fef07eb5ff23051f7d36ac359f748c7a6461"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8331W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8331w = "${FIRMWARE_DIR}/${FIRMWARE_DB8331W}"

