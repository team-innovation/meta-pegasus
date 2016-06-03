DESCRIPTION = "Firmware for psoc5 on slimline."
LICENSE = "CLOSED"

#inherit module

PR = "r3"
PV = "0.1"

SRC_URI = " \
	file://ihex2fw.c \
	git://git.vivint.com/cap-touch;branch=udb \
"

FW_DIR = "/lib/firmware/vivint"
FW_NAME = "sly-psoc5.fw"

SRCREV = "${AUTOREV}"

S = "${WORKDIR}"

do_compile() {
	${BUILD_CC} ihex2fw.c -o ihex2fw
	./ihex2fw \
		git/firmware/design/LEDs.cydsn/CortexM3/ARM_GCC_493/Debug/LEDs.hex \
		${FW_NAME}
}

do_install() {
	install -d ${D}${FW_DIR}
	cp ${FW_NAME} ${D}${FW_DIR}
	chmod 644 ${D}${FW_DIR}/${FW_NAME}
}

FILES_${PN} = "${FW_DIR}/${FW_NAME}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
