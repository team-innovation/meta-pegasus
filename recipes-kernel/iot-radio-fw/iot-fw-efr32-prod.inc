DESCRIPTION = "EFR32 rail test firmware"

PR = "r6"

EFR32_FW_HEX_NAME = "vivint_efr32_fg14.hex"
EFR32_FW_NAME = "vivint_efr32.fw"

SRC_URI += "file://${EFR32_FW_HEX_NAME}"

do_compile_append(){
        ./ihex2fw ${EFR32_FW_HEX_NAME} ${EFR32_FW_NAME}
}

do_install_append () {
    install -m 644 ${S}/${EFR32_FW_NAME} ${D}${FW_DIR}
}

FILES_${PN}-efr32 += "\
    ${FW_DIR}/${EFR32_FW_NAME} \
    "

