SUMMARY = "Sierra Wireless Download Tool"
DESCRIPTION = "Tool for updating firmware on Sierra Wireless modems"
AUTHOR = "dwen@sierrawireless.com"
SECTION = "base"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://src/libusb.h;md5=dc8c37e89bf7848579d633efc97626d2"
PR = "r1"

#SRC_URI = "http://updateseng.vivint.com/innovation/downloads/Sierra_HL7xxx_DownloadTool-${PV}.tgz"
SRC_URI = "file://Sierra_HL7xxx_DownloadTool-${PV}.tgz"

DEPENDS = "libusb1"

S = "${WORKDIR}"

do_compile() {
	make
}

do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${S}/bin/download ${D}/usr/bin/swdltool
}
