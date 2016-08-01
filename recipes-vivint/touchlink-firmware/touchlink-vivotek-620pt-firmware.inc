PKGR_${PN}-620pt = "${PR}.9"

FIRMWARE_620PT = "PT8133-ALAM-0102b5.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_620PT};name=620pt"

SRC_URI[620pt.md5sum] = "5772b1e4e4aa79b04c0c5ba512db6c89"
SRC_URI[620pt.sha256sum] = "9176973aa2eddf62661c337815e4c1c01e0d13751bb36a4cd7f31b82272152ad"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_620PT} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-620pt = "${FIRMWARE_DIR}/${FIRMWARE_620PT}"
