LICENSE = "CLOSED"

PV = "1.0.0"
PR = "r4"

SRC_URI = "file://touchtest"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}"

do_config(){
:
}

do_compile() {
:
}

do_install() {
	install -d ${D}/usr/local/bin
	install -m 0755 ${WORKDIR}/touchtest ${D}/usr/local/bin
}

FILES_${PN} = "/usr/local/bin/touchtest"
