DESCRIPTION = "Zwave rail test firmware"

require iot-fw.inc

PR = "r2"

FW_HEX_NAME = "railtest_zwave.hex"
FW_NAME = "railtest_zwave.fw"

do_install_append(){
        install -d ${D}/home/root
        touch ${D}/home/root/.mfg_image
}

FILES_${PN} += "\
    /home/root/.mfg_image \
    "
