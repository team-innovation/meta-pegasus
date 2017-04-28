PKGR_${PN}-db8332w = "${PR}.22"

FIRMWARE_DB8332W = "DB8332W-VINT-0200b.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8332W};name=db8332w"

SRC_URI[db8332w.md5sum] = "2c69a253c45a2c256b791e6a8969548d"
SRC_URI[db8332w.sha256sum] = "a3db1f294d624c494a6199943f1071dc55cb69529f5d5ebe91ee3510eec9f5c0"
 
do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8332W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8332w = "${FIRMWARE_DIR}/${FIRMWARE_DB8332W}"
