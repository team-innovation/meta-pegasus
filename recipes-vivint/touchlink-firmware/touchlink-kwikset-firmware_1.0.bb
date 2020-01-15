DESCRIPTION = "Kwikset lock firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"
PR = "r51"

FIRMWARE_MCU    = "Kwikset2017_0090_0003_0541_4100_26a3_65.05.hex"
FIRMWARE_ZWAVE  =  "Kwikset2017_0090_0003_0541_1400_59d7_4.96.hex"
FIRMWARE_MCU_912    = "Kwikset912_0090_0003_0339_3900_b728_57.07.hex"
FIRMWARE_ZWAVE_912  = "Kwikset912_0090_0003_0339_1400_b012_4.10.hex"

FIRMWARE_DIR = "/var/lib/firmware/zwave"


SRC_URI = "file://${FIRMWARE_MCU} \
           file://${FIRMWARE_ZWAVE} \
           file://${FIRMWARE_MCU_912} \
           file://${FIRMWARE_ZWAVE_912}"

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
     cp ${WORKDIR}/${FIRMWARE_MCU_912} ${D}/${FIRMWARE_DIR}
     cp ${WORKDIR}/${FIRMWARE_ZWAVE_912} ${D}/${FIRMWARE_DIR}
}

FILES_${PN} = "${FIRMWARE_DIR}/*"
