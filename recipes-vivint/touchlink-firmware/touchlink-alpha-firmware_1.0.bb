DESCRIPTION = "Alpha Network video camera firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"

FIRMWARE_CS6022_OV4689 = "update_CS-6022_1.0.0_ov4689_239.bin"
FIRMWARE_DIR = "/var/lib/firmware/AlphaNetwork"

SRC_URI = "http://${UPDATESENG}/camera_firmwares/Alpha/${FIRMWARE_CS6022_OV4689}"

SRC_URI[md5sum] = "9d384fb71cb3627e868627cd3a2e073f"
SRC_URI[sha256sum] = "ed7ce2281275ed7e0e020c25045eeeeca2a0042df9f3bad2079df6be6d8500ed"

PV_prepend = "1"
PKGR_${PN}-cs6022-ov4689 = "${PR}.44"

do_compile() {
     :
}

do_configure() {
     :
}

do_compileconfigs() {
     :
}

do_setscene() {
     :
}

do_distribute_sources() {
     :
}

do_create_srcipk() {
     :
}

do_copy_license() {
     :
}

do_install() {
     install -d ${D}/${FIRMWARE_DIR}
     cp ${WORKDIR}/${FIRMWARE_CS6022_OV4689} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-cs6022-ov4689 = "${FIRMWARE_DIR}/${FIRMWARE_CS6022_OV4689}"
FILES_${PN}-cs6022-lic = "${FIRMWARE_DIR}/README"

PACKAGES = "${PN}-cs6022-lic \
	    ${PN}-cs6022-ov4689 \
           "
