DESCRIPTION = "Kwikset lock firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"
PR = "r45"

FIRMWARE_MCU    = "Kwikset2017_0090_0003_0541_4100_e40c_65.03.hex"
FIRMWARE_ZWAVE  = "Kwikset2017_0090_0003_0541_1400_5a18_4.70.hex"

FIRMWARE_DIR = "/var/lib/firmware/zwave"


SRC_URI = "file://${FIRMWARE_MCU} \
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
     
     cp ${WORKDIR}/${FIRMWARE_MCU} ${D}/${FIRMWARE_DIR}
     cp ${WORKDIR}/${FIRMWARE_ZWAVE} ${D}/${FIRMWARE_DIR}
}

FILES_${PN} = "${FIRMWARE_DIR}/*"
