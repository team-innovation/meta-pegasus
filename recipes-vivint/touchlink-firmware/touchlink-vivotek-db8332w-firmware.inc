PKGR_${PN}-db8332w = "${PR}.18"

FIRMWARE_DB8332W = "DB8332W-VINT-0100y.flash.pkg"

SRC_URI += "http://${UPDATESENG}/camera_firmwares/Vivotek/${FIRMWARE_DB8332W};name=db8332w"

SRC_URI[db8332w.md5sum] = "32f29d0c9ea3f22121195eba3bc292d9"
SRC_URI[db8332w.sha256sum] = "727024bc35069270aca6cf2476fb0de0c39c0d00459c18cf1ceed3fa1feb5beb"

do_install_append() {
     cp ${WORKDIR}/${FIRMWARE_DB8332W} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-db8332w = "${FIRMWARE_DIR}/${FIRMWARE_DB8332W}"
