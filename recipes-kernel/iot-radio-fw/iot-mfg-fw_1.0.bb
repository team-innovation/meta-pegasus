HOMEPAGE = "http://www.vivint.com/"
SECTION = "base"
LICENSE = "CLOSED"

require iot-fw-ble.inc
require iot-fw-zwave.inc
require iot-fw-efr32.inc


SRC_URI += "file://ihex2fw.c"

FW_DIR = "/lib/firmware/vivint"

S = "${WORKDIR}"

do_compile() {
        ${BUILD_CC} ihex2fw.c -o ihex2fw
}

do_install () {
    install -d ${D}${FW_DIR}
}

PACKAGES = "${PN}-ble	\
	    ${PN}-zwave \
	    ${PN}-efr32 \
	   "


