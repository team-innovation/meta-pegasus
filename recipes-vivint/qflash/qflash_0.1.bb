SUMMARY = "Quectel modem flash tool"
DESCRIPTION = "Tool for updating firmware on Quectel modems"
AUTHOR = "Quectel"
SECTION = "base"
LICENSE = "CLOSED"
PR = "1.3.2"

#SRC_URI = "http://updateseng.vivint.com/innovation/downloads/Quectel_QFlash.tar.gz"
SRC_URI = "file://Quectel_QFlash.tar.gz"

DEPENDS = ""
PACKAGE_ARCH = "${MACHINE_ARCH}"
TARGET_CC_ARCH += "${LDFLAGS}"
S = "${WORKDIR}"

do_compile() {
	make
}

do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${S}/qflash ${D}/usr/bin/qflash
}
