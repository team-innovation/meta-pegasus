SUMMARY = "Vivint authored utilities catch-all package"
DESCRIPTION = "Various Vivint authored utilities for development and hw test"
SECTION = "utilities"
LICENSE = "CLOSED"
PV = "1.0.0"
PR = "r5"

PACKAGES = "${PN} ${PN}-dbg"

SRC_URI = "\
	   file://ccat \
	   file://ctail \
	   file://dot.profile \
	   file://gadgetsetup \
	   file://resize.c \
	   file://slimline-initemmc \
	   file://wlan-hwtest \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"
S = "${WORKDIR}"

do_compile() {
	${CC} resize.c -o resize
}

do_install() {
	install -d ${D}/usr/local/bin
	install -m 0755 ${S}/ctail ${D}/usr/local/bin
	install -m 0755 ${S}/ccat ${D}/usr/local/bin
	install -m 0755 ${S}/gadgetsetup ${D}/usr/local/bin
	install -m 0755 ${S}/slimline-initemmc ${D}/usr/local/bin
	install -m 0755 ${S}/resize ${D}/usr/local/bin
	install -m 0755 ${S}/wlan-hwtest ${D}/usr/local/bin

	install -d ${D}/home/root/
	install -m 0755 ${S}/dot.profile ${D}/home/root/.profile
}

FILES_${PN}-dbg += "/usr/local/bin/.debug/"
FILES_${PN} += "/home/root /usr/local/bin/*"
