PKGR_${PN}-db8332w = "${PR}.14"

FIRMWARE_DB8332W = "DB8332W-VINT-0100s.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8332W};name=db8332w"

SRC_URI[db8332w.md5sum] = "c99bd8aa11830bd468b898dbafcf468c"
SRC_URI[db8332w.sha256sum] = "131678149581b2ed5403c54d1ed8c63dc8dba1e59fd0df26986fc1f0332a2020"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8332W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8332w = "${FIRMWARE_DIR}/${FIRMWARE_DB8332W}"
