LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"

PV_prepend = "1"

require touchlink-lgit-poe-bridge-firmware.inc

FIRMWARE_DIR = "/var/lib/firmware/LGIT"

do_compile() {
     :
}

do_configure() {
     :
}

do_compileconfigs() {
     :
}

do_setscene() {
     :
}

do_distribute_sources() {
     :
}

do_create_srcipk() {
     :
}

do_copy_license() {
     :
}

do_install() {
	install -d ${D}/${FIRMWARE_DIR}
}

PACKAGES = "${PN}-poe-bridge"
