DESCRIPTION = "Maxtouch configuration files"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"
PR = "r1"

FIRMWARE_sly = "maxtouch-sly.cfg"
FIRMWARE_slim_line  = "maxtouch-slimline.cfg"
FIRMWARE_wallsly  = "maxtouch-wallsly_haier.cfg"
FIRMWARE_wallsly2  = "maxtouch-wallsly_nvd.cfg"

FIRMWARE_DIR = "/lib/firmware"

MAXTOUCH_INIT = "maxtouch_check.sh"

SRC_URI = "file://${FIRMWARE_sly} \
            file://${FIRMWARE_slim_line} \
            file://${FIRMWARE_wallsly} \
            file://${FIRMWARE_wallsly2} \
            file://${MAXTOUCH_INIT} \
           "

inherit update-rc.d

INITSCRIPT_NAME = "${MAXTOUCH_INIT}"
INITSCRIPT_PARAMS = "start 07 S ."

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
     
    install -m 0644 ${WORKDIR}/${FIRMWARE_sly} ${D}/${FIRMWARE_DIR}
    install -m 0644 ${WORKDIR}/${FIRMWARE_wallsly} ${D}/${FIRMWARE_DIR}
    install -m 0644 ${WORKDIR}/${FIRMWARE_wallsly2} ${D}/${FIRMWARE_DIR}
    install -m 0644 ${WORKDIR}/${FIRMWARE_slim_line} ${D}/${FIRMWARE_DIR}

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/${MAXTOUCH_INIT} ${D}${sysconfdir}/init.d
}

FILES_${PN} = "${FIRMWARE_DIR}/* ${sysconfdir}/init.d/${MAXTOUCH_INIT}"
