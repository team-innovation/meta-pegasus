DESCRIPTION = "Maxtouch configuration files"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"
PR = "r1"

FIRMWARE_sly = "maxtouch-sly.cfg"
FIRMWARE_slim_line  = "maxtouch-slimline.cfg"
FIRMWARE_wallsly  = "maxtouch-wallsly.cfg"
FIRMWARE_wallsly2  = "maxtouch-wallsly2.cfg"

FIRMWARE_DIR = "/lib/firmware"


SRC_URI = "file://${FIRMWARE_sly} \
            file://${FIRMWARE_slim_line} \
            file://${FIRMWARE_wallsly} \
            file://${FIRMWARE_wallsly2} \
           "

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
     
     cp ${WORKDIR}/${FIRMWARE_sly} ${D}/${FIRMWARE_DIR}
     cp ${WORKDIR}/${FIRMWARE_wallsly} ${D}/${FIRMWARE_DIR}
     cp ${WORKDIR}/${FIRMWARE_wallsly2} ${D}/${FIRMWARE_DIR}
     cp ${WORKDIR}/${FIRMWARE_slim_line} ${D}/${FIRMWARE_DIR}
}

FILES_${PN} = "${FIRMWARE_DIR}/*"
