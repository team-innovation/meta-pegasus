DESCRIPTION = "Alpha Network video camera firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"

FIRMWARE_CS6022_OV4689 = "update_CS-6022_1.1.0_ov4689_304.bin"
FIRMWARE_DIR = "/var/lib/firmware/AlphaNetwork"

SRC_URI = "http://${UPDATESENG}/camera_firmwares/Alpha/${FIRMWARE_CS6022_OV4689}"

SRC_URI[md5sum] = "1eb55f7a3077c2c7d161a045582b3409"
SRC_URI[sha256sum] = "6ee3a456185d196bc2c1b7f32a4d539c0c16b02da4802c70ac4686f1a59382b5"
 
PV_prepend = "1"
PKGR_${PN}-cs6022-ov4689 = "${PR}.50"

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
