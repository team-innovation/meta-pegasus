LICENSE = "CLOSED"

PV = "1.0.0"
PR = "r11"

SRC_URI = "file://ctail \
	   file://ccat"

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
	install -m 0755 ${WORKDIR}/ctail ${D}/usr/local/bin
	install -m 0755 ${WORKDIR}/ccat ${D}/usr/local/bin
}

FILES_${PN} = "/usr/local/bin/*"
