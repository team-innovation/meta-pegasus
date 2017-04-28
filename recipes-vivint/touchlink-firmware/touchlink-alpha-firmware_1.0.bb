DESCRIPTION = "Alpha Network video camera firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"

FIRMWARE_CS6022_OV4689 = "update_CS-6022_1.1.1_ov4689_340.bin"
FIRMWARE_DIR = "/var/lib/firmware/AlphaNetwork"

SRC_URI = "http://${UPDATESENG}/camera_firmwares/Alpha/${FIRMWARE_CS6022_OV4689}"

SRC_URI[md5sum] = "998ef4c8009692ea660937d625d6bd0a"
SRC_URI[sha256sum] = "ed82f95a559fc3ea91bfd698a40e35c3381bd4994c1996e56c12d21bc4880d12"
 
PV_prepend = "1"
PKGR_${PN}-cs6022-ov4689 = "${PR}.52"

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
