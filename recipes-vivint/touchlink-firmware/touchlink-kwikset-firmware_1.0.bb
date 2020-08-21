DESCRIPTION = "Kwikset lock firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"
PR = "r52"

FIRMWARE_MCU    = "Kwikset2017_0090_0003_0541_4100_26a3_65.05.hex"
FIRMWARE_ZWAVE  =  "Kwikset2017_0090_0003_0541_1400_59d7_4.96.hex"
FIRMWARE_MCU_912    = "Kwikset912_0090_0003_0339_3900_b728_57.07.hex"
FIRMWARE_ZWAVE_912  = "Kwikset912_0090_0003_0339_1400_b012_4.10.hex"
FIRMWARE_MCU_OBSIDIAN    = "KwiksetObsidian_0090_0003_0742_4200_ab56_66.31.hex"
FIRMWARE_ZWAVE_OBSIDIAN  = "KwiksetObsidian_0090_0003_0742_1400_2e40_4.32.hex"
FIRMWARE_ZWAVE_892 = "Kwikset892_0090_0811_03A8_11a8_3ab3_7.07.hex"

FIRMWARE_DIR = "/var/lib/firmware/zwave"


SRC_URI = "file://${FIRMWARE_MCU} \
           file://${FIRMWARE_ZWAVE} \
           file://${FIRMWARE_MCU_912} \
           file://${FIRMWARE_ZWAVE_912} \
           file://${FIRMWARE_MCU_OBSIDIAN} \
           file://${FIRMWARE_ZWAVE_OBSIDIAN} \
           file://${FIRMWARE_ZWAVE_892}"

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
     cp ${WORKDIR}/${FIRMWARE_MCU_OBSIDIAN} ${D}/${FIRMWARE_DIR}
     cp ${WORKDIR}/${FIRMWARE_ZWAVE_OBSIDIAN} ${D}/${FIRMWARE_DIR}
     cp ${WORKDIR}/${FIRMWARE_ZWAVE_892} ${D}/${FIRMWARE_DIR}
}

FILES_${PN} = "${FIRMWARE_DIR}/*"
