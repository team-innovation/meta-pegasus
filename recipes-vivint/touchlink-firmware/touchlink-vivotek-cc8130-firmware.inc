PKGR_${PN}-cc8130 = "${PR}.3"

FIRMWARE_CC8130 = "CC8130-VINT-0201a3.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_CC8130};name=cc8130"

SRC_URI[cc8130.md5sum] = "8580554eadeb8a480e67e48880b31a2b"
SRC_URI[cc8130.sha256sum] = "be14a7566397eb4aa3f9dfd58f1590c4af281c26ecef63147fc71a71d52589a7"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_CC8130} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-cc8130 = "${FIRMWARE_DIR}/${FIRMWARE_CC8130}"

