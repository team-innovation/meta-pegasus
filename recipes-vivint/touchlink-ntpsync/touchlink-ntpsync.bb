LICENSE = "CLOSED"

PV = "1.0.0"
PR = "r11"

SRC_URI = "file://ntpsync"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

do_config(){
:
}

do_compile() {
:
}

do_install() {
	install -d ${D}/usr/local/bin
	install -m 0755 ${WORKDIR}/ntpsync ${D}/usr/local/bin
}

FILES_${PN} = "/usr/local/bin/*"
