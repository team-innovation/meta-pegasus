DESCRIPTION = "Radio Thermostat Company of America thermostat firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"
PR = "r42"

FIRMWARE_PIC    = "CT200-PIC-0.34.00_0098_C801_0071_D2B2_0.34.hex"
FIRMWARE_UI     = "CT200-UI-0.10.00_0098_C801_0072_9FF5_0.10.hex"
FIRMWARE_ZWAVE  = "CT200-ZWAVE-11.27_0098_C801_0043_683E_11.27.hex"

FIRMWARE_DIR = "/var/lib/firmware/zwave"


SRC_URI = "file://${FIRMWARE_PIC} \
           file://${FIRMWARE_UI} \
           file://${FIRMWARE_ZWAVE}"

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
     
     cp ${WORKDIR}/${FIRMWARE_PIC} ${D}/${FIRMWARE_DIR}
     cp ${WORKDIR}/${FIRMWARE_UI} ${D}/${FIRMWARE_DIR}
     cp ${WORKDIR}/${FIRMWARE_ZWAVE} ${D}/${FIRMWARE_DIR}
}

FILES_${PN} = "${FIRMWARE_DIR}/*"
