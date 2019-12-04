SUMMARY = "Telit Flash Tool"
DESCRIPTION = "Tool for updating firmware on Telit modems"
AUTHOR = "Telit"
SECTION = "base"
LICENSE = "CLOSED"
PR = "r1"

#SRC_URI = "http://updateseng.vivint.com/innovation/downloads/Telit_LE910_lxfp.tgz"
SRC_URI = "file://Telit_LE910_lxfp.tgz"

#DEPENDS = "libusb1"

S = "${WORKDIR}"

do_compile() {
	make
}

do_install() {
	install -d ${D}/usr/bin
	install -m 0755 ${S}/bin/lxfp ${D}/usr/bin/lxfp
}

