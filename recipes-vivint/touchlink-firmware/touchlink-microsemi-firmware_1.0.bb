DESCRIPTION = "MicroSmei ZL380 firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"
PR = "r1"

FIRMWARE_ZL380    = "MICROSEMI_firmware.s3"

FIRMWARE_DIR = "/lib/firmware"


SRC_URI = "file://${FIRMWARE_ZL380} "

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
     
     cp ${WORKDIR}/${FIRMWARE_ZL380} ${D}/${FIRMWARE_DIR}
}

FILES_${PN} = "${FIRMWARE_DIR}/*"
