PKGR_${PN}-db8332w = "${PR}.24"

FIRMWARE_DB8332W = "DB8332W-VINT-0200d.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8332W};name=db8332w"

SRC_URI[db8332w.md5sum] = "bffe5291d52f8c015ce1299d1bd0c769"
SRC_URI[db8332w.sha256sum] = "15a23aa5b124fb637ec46c83b938f225a33c2c45f26069f0567cc368a94ad21c"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8332W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8332w = "${FIRMWARE_DIR}/${FIRMWARE_DB8332W}"
