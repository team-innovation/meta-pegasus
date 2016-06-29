DESCRIPTION = "Alpha Network video camera firmware"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://../camera-firmwares/Alpha/README;md5=b30f2526a2a1704f1edb4469b86216b8"
HOMEPAGE = "http://www.vivint.com"
PR = "3"

PKGR_${PN}-cs6022 = "${PR}.19"
PKGR_${PN}-cs6022-ov4689 = "${PR}.34"

FIRMWARE_CS6022 = "update_CS-6022_1.0.0_260.bin"
FIRMWARE_CS6022_OV4689 = "update_CS-6022_1.0.0_ov4689_*.bin"

FIRMWARE_DIR = "/var/lib/firmware/AlphaNetwork"

SRC_URI = "file://${FIRMWARE_CS6022} \
	   file://${FIRMWARE_CS6022_OV4689} \
	  "

SRCREV = "${AUTOREV}"
SRC_URI = "hg://${HG_SERVER};module=camera-firmwares/Alpha;protocol=http"
PV = "${SRCPV}"

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
     cp ${WORKDIR}/camera-firmwares/Alpha/* ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-cs6022 = "${FIRMWARE_DIR}/${FIRMWARE_CS6022}"
FILES_${PN}-cs6022-ov4689 = "${FIRMWARE_DIR}/${FIRMWARE_CS6022_OV4689}"
FILES_${PN}-cs6022-lic = "${FIRMWARE_DIR}/README"

PACKAGES = "${PN}-cs6022 \
			${PN}-cs6022-lic \
			${PN}-cs6022-ov4689 \
           "
