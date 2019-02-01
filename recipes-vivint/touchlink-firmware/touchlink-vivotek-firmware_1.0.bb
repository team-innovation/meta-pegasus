DESCRIPTION = "Vivotek camera firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"

PV_prepend = "1"

require touchlink-vivotek-520ir-firmware.inc  
require touchlink-vivotek-721w-firmware.inc  
require touchlink-vivotek-hd400w-firmware.inc  
require touchlink-vivotek-db8331w-firmware.inc  
require touchlink-vivotek-db8332w-firmware.inc

FIRMWARE_DIR = "/var/lib/firmware/Vivotek"

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

PACKAGES = "${PN}-520ir \
	    ${PN}-520ir-lic \
            ${PN}-721w \
            ${PN}-hd400w \
            ${PN}-db8331w \
            ${PN}-db8332w"

