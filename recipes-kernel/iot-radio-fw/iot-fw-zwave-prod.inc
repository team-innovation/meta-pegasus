DESCRIPTION = "Zwave firmware"

SRC_URI += "file://vivint_zwave.hex"

PR = "r5"

ZWAVE_FW_HEX_NAME = "vivint_zwave.hex"
ZWAVE_FW_NAME = "vivint_zwave.fw"

do_compile_append(){
        ./ihex2fw ${ZWAVE_FW_HEX_NAME} ${ZWAVE_FW_NAME}
}

do_install_append () {
    install -m 644 ${S}/${ZWAVE_FW_NAME} ${D}${FW_DIR}
}

FILES_${PN}-zwave = "\
    ${FW_DIR}/${ZWAVE_FW_NAME} \
    "
