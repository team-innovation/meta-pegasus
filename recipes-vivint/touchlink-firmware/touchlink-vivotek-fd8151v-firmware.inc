PKGR_${PN}-fd8151v = "${PR}.5"

FIRMWARE_FD8151V = "FD8151V-VINT-0103a5.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_FD8151V};name=fd8151v"

SRC_URI[fd8151v.md5sum] = "24a9ae2e6d2955cb0c33233fa2bb2a71"
SRC_URI[fd8151v.sha256sum] = "ddff83c5c318eb8b087bd202e2b9d99ca3af03f6c75351c21bf1f7eab9599887"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_FD8151V} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-fd8151v = "${FIRMWARE_DIR}/${FIRMWARE_FD8151V}"

