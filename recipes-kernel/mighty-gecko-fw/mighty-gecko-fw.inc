DESCRIPTION = "Firmware for mighty gecko on wallsly."
LICENSE = "CLOSED"

#inherit module

PR = "r3"
PV = "0.2"

SRC_URI = "file://ihex2fw.c "

FW_DIR = "/lib/firmware/vivint"

SRCREV = "${AUTOREV}"

S = "${WORKDIR}"

do_compile() {
	${BUILD_CC} ihex2fw.c -o ihex2fw
	./ihex2fw ${FW_HEX_NAME} ${FW_NAME}
	./ihex2fw ${SINK_FW_HEX_NAME} ${SINK_FW_NAME}
}

do_install() {
	install -d ${D}${FW_DIR}
	install -m 644 ${FW_NAME} ${D}${FW_DIR}
	install -m 644 ${SINK_FW_NAME} ${D}${FW_DIR}
}

FILES_${PN} = "${FW_DIR}/*"

PACKAGE_ARCH = "${MACHINE_ARCH}"
