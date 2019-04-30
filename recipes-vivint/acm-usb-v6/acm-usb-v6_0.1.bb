SUMMARY = "Sierra Wireless USB Log Download Tool"
DESCRIPTION = "Tool for retrieving logs via libusb from Sierra Wireless modems"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://acm_usb_v6.c;md5=7a6306e18f2e8c8def707707f2adeb74"
PR = "r1"

SRC_URI = "file://acm_usb_v6.c"

DEPENDS = "libusb1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
TARGET_CC_ARCH += "${LDFLAGS}"
S = "${WORKDIR}"

do_compile() {
	${CC} acm_usb_v6.c $(CFLAGS) -lusb-1.0 -g -O9 -o acm_usb_v6
}

do_install() {
	install -d ${D}/usr/local/bin
	install -m 0755 ${S}/acm_usb_v6 ${D}/usr/local/bin/acm_usb_v6
}

FILES_${PN}-dbg = "/usr/src/debug /usr/local/bin/.debug"
FILES_${PN} = "/usr/local/bin/"
