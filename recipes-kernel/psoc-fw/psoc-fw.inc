DESCRIPTION = "Firmware for psoc5 on slimline."
LICENSE = "CLOSED"

#inherit module

PR = "r55"
PV = "0.2"

SRC_URI = "file://ihex2fw.c "

FW_DIR = "/lib/firmware/vivint"

SRCREV = "${AUTOREV}"

S = "${WORKDIR}"

do_compile() {
	${BUILD_CC} ihex2fw.c -o ihex2fw
	./ihex2fw ${FW_HEX_NAME} ${FW_NAME}
}

do_install() {
	install -d ${D}${FW_DIR}
	cp ${FW_NAME} ${D}${FW_DIR}
	chmod 644 ${D}${FW_DIR}/${FW_NAME}
}

FILES_${PN} = "${FW_DIR}/${FW_NAME}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
