DESCRIPTION = "Quectel EG91 firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.quectel.com"
PR = "r01"

PKGR_${PN}-eg91-f = "${PR}.1"

FIRMWARE_eg91_f = "FOTA_EG91NAFBR05A04_01.004.01.004-R05A05_01.005.01.005.zip"

FIRMWARE_DIR = "/var/lib/firmware/Quectel"

SRC_URI = "file://${FIRMWARE_eg91_f}"

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
     cp ${WORKDIR}/${FIRMWARE_eg91_f} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-eg91-f = "${FIRMWARE_DIR}/${FIRMWARE_eg91_f}"

PACKAGES = "${PN}-eg91-f"
