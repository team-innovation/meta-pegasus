PKGR_${PN}-db8332w = "${PR}.15"

FIRMWARE_DB8332W = "DB8332W-VINT-0100t.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8332W};name=db8332w"

SRC_URI[db8332w.md5sum] = "c36f6ec8ff42f73e1d9a423ffc8cc2fc"
SRC_URI[db8332w.sha256sum] = "fbef4600e6a180dcf49d8229bb4591a9cf72215c59f1f64f81ae13f990de54ff"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8332W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8332w = "${FIRMWARE_DIR}/${FIRMWARE_DB8332W}"
