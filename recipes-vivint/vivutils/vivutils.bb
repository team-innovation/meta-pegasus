DESCRIPTION = "Various Vivint authored utilities"
SECTION = "utilities"
LICENSE = "CLOSED"
PV = "1.0.0"
PR = "r2"

SRC_URI = "file://ctail \
	   file://ccat	\
"
# file://resize.c

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}"

do_compile() {
	:
	#${CC} resize.c -o resize
}

do_install() {
	install -d ${D}/usr/local/bin
	install -m 0755 ctail ${D}/usr/local/bin
	install -m 0755 ccat ${D}/usr/local/bin
	#install -m 0755 resize ${D}/usr/local/bin
}

FILES_${PN} = "/usr/local/bin/*"
