DESCRIPTION = "EFR32 rail test firmware"

SRC_URI += "file://vivint_efr32.hex"


PR = "r3"

EFR32_FW_HEX_NAME = "vivint_efr32.hex"
EFR32_FW_NAME = "vivint_efr32.fw"

do_compile_append(){
        ./ihex2fw ${EFR32_FW_HEX_NAME} ${EFR32_FW_NAME}
}


do_install_append(){
	install -d ${D}/home/root
	touch ${D}/home/root/.mfg_image
}

do_install () {
    install -m 644 ${S}/${EFR32_FW_NAME} ${D}${FW_DIR}
}

FILES_${PN}-efr32 += "\
    ${FW_DIR}/${EFR32_FW_NAME} \
    /home/root/.mfg_image \
    "

