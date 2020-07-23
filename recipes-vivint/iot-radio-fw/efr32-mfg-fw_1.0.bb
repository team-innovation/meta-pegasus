DESCRIPTION = "Bluetooth rail test firmware"

require iot-fw.inc

PR = "r1"

FW_NAME = "vivint_efr32.hex"

do_install_append(){
	install -d ${D}/home/root
	touch ${D}/home/root/.mfg_image
}

FILES_${PN} += "\
    /home/root/.mfg_image \
    "

