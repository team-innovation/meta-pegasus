PKGR_${PN}-db8331w = "${PR}.28"

FIRMWARE_DB8331W = "DB8331W-VINT-0101p.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8331W};name=db8331w"

SRC_URI[db8331w.md5sum] = "0a5ed51050feb817385b2ef52dcc9af8"
SRC_URI[db8331w.sha256sum] = "8db16e8bee88d7a2bbc522a32616f0b666574c2011417126e78803b9a4538bdb"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8331W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8331w = "${FIRMWARE_DIR}/${FIRMWARE_DB8331W}"

