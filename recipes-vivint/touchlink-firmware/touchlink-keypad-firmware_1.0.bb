DESCRIPTION = "Vivint Keypad Firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"
PR = "r54"

FIRMWARE_VIVINT_KEYPAD = "VivintKeypad_0156_4B50_0001_5001_e670_1.5.hex"

FIRMWARE_DIR = "/var/lib/firmware/zwave"


SRC_URI = "file://${FIRMWARE_VIVINT_KEYPAD}"

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

     cp ${WORKDIR}/${FIRMWARE_VIVINT_KEYPAD} ${D}/${FIRMWARE_DIR}
}

FILES_${PN} = "${FIRMWARE_DIR}/*"
