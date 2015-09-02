DESCRIPTION = "Various Vivint authored utilities"
SECTION = "utilities"
LICENSE = "CLOSED"
PV = "1.0.0"
PR = "r2"

PACKAGES = "${PN} ${PN}-dbg"

SRC_URI = "\
	   file://ccat \
	   file://ctail \
	   file://gadgetsetup \
	   file://resize.c \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}"

do_compile() {
	${CC} resize.c -o resize
}

do_install() {
	install -d ${D}/usr/local/bin
	install -m 0755 ctail ${D}/usr/local/bin
	install -m 0755 ccat ${D}/usr/local/bin
	install -m 0755 gadgetsetup ${D}/usr/local/bin
	install -m 0755 resize ${D}/usr/local/bin
}

FILES_${PN}-dbg += "/usr/local/bin/.debug/"
FILES_${PN} += "/usr/local/bin/*"
