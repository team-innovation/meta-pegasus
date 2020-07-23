DESCRIPTION = "Zwave rail test firmware"

require iot-fw.inc

PR = "r1"

FW_NAME = "railtest_zwave.hex"

do_install_append(){
        install -d ${D}/home/root
        touch ${D}/home/root/.mfg_image
}

FILES_${PN} += "\
    /home/root/.mfg_image \
    "
