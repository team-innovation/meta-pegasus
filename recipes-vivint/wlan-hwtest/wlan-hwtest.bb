SUMMARY = "Simple test wlan module"
DESCRIPTION = "Uses iw commands and an unencrypted access point"
SECTION = "base"
LICENSE = "CLOSED"

RDEPENDS_${PN} += "iw"

SRC_URI = "file://wlan-hwtest \
"

do_install () {
	install -d ${D}/usr/bin/
	install -m 0755 ${WORKDIR}/wlan-hwtest ${D}/usr/bin/wlan-hwtest
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
