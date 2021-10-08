DESCRIPTION = "Vivint Keypad Firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"
PR = "r59"

FIRMWARE_VIVINT_KEYPAD = "VivintKeypad_0156_4B50_0001_5001_998f_1.10.hex"

FIRMWARE_DIR = "/var/lib/firmware/zwave"


SRC_URI = "file://${FIRMWARE_VIVINT_KEYPAD}"

do_compile[noexec] = "1"

do_configure[noexec] = "1"

do_compileconfigs[noexec] = "1"

do_setscene[noexec] = "1"

do_distribute_sources[noexec] = "1"

do_create_srcipk[noexec] = "1"

do_copy_license[noexec] = "1"

do_install() {
     install -d ${D}/${FIRMWARE_DIR}

     cp ${WORKDIR}/${FIRMWARE_VIVINT_KEYPAD} ${D}/${FIRMWARE_DIR}
}

FILES_${PN} = "${FIRMWARE_DIR}/*"
