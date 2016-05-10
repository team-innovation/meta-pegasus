DESCRIPTION = "Flash zwave firmware on first boot up"
HOMEPAGE = "http://www.vivint.com/"
LICENSE = "CLOSED"
PR = "r1"

RDEPENDS_${PN} = " \
    touchlink-apps \
    python3-intelhex \
    python3-pyserial \
"

SRC_URI = " \
	file://serialapi_controller_static_zw050x_us.hex \
	"

FW_NAME = "serialapi_controller_static_zw050x_us.hex"
FW_DIR = "/lib/firmware/vivint"

do_compile() {
	:
}

do_runstrip() {
        :
}

do_install () {
    install -d ${D}${FW_DIR}
    cp ${WORKDIR}/${FW_NAME} ${D}${FW_DIR}
    chmod 644 ${D}${FW_DIR}/${FW_NAME}
}

pkg_postinst_${PN} () {
   #!/bin/sh -e
   # Program the zwave device on first boot
   if [ x"$D" = "x" ]; then
        if [ -e /opt/2gig/zwaved/scripts/zwave_program.pyo]
        then
            logging "Flashing zwave firmware..."
            python3 /opt/2gig/zwaved/scripts/zwave_program.pyo ${FW_DIR}/${FW_NAME} 
        else
            logging "zwave_program.pyo does not exist, cannot flash zwave chip"
        fi
    else
        exit 1
    fi
}

FILES_${PN} = "${FW_DIR}/${FW_NAME}"

