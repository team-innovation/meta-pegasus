LICENSE = "CLOSED"

PV = "1.0.0"
PR = "r13"

SRC_URI = "file://ntpsync"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

do_config[noexec] = "1"

do_compile[noexec] = "1" 

RDEPENDS_${PN} += "bash"

do_install() {
	install -d ${D}/usr/local/bin
	install -m 0755 ${WORKDIR}/ntpsync ${D}/usr/local/bin
}

FILES_${PN} = "/usr/local/bin/*"
