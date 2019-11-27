DESCRIPTION = "RCS thermostat firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"
PR = "r17"

FIRMWARE_MSP430 = "EV2-ZWAVE_0156_5448_0001_5449_38ae_00.28.hex"

FIRMWARE_DIR = "/var/lib/firmware/zwave"


SRC_URI = "file://${FIRMWARE_MSP430}"

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
     
     cp ${WORKDIR}/${FIRMWARE_MSP430} ${D}/${FIRMWARE_DIR}
}

FILES_${PN} = "${FIRMWARE_DIR}/*"
