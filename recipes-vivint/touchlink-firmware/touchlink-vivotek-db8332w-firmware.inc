PKGR_${PN}-db8332w = "${PR}.28"

FIRMWARE_DB8332W = "DB8332W-VINT-0200l.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8332W};name=db8332w"

SRC_URI[db8332w.md5sum] = "ac31efacd2317ef0362893c27548aa99"
SRC_URI[db8332w.sha256sum] = "0ad77ce4e0126d23b5f30f1c06e76782d845d9612fc4cb84180df8bffa0c159b"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8332W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8332w = "${FIRMWARE_DIR}/${FIRMWARE_DB8332W}"
