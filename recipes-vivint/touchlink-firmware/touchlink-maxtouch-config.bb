DESCRIPTION = "Maxtouch configuration files"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"
PR = "r7"

RDEPENDS_${PN} += "bash"
FIRMWARE_sly = "maxtouch-sly.cfg"
FIRMWARE_slim_line  = "maxtouch-slimline.cfg"
FIRMWARE_wallsly  = "maxtouch-wallsly_haier.cfg"
FIRMWARE_wallsly2  = "maxtouch-wallsly_nvd.cfg"
FIRMWARE_wallsly3  = "maxtouch-wallsly_tianma.cfg"
FIRMWARE_wallsly4  = "maxtouch-wallsly_boe.cfg"


FIRMWARE_DIR = "/lib/firmware"

MAXTOUCH_INIT = "maxtouch_check.sh"

SRC_URI = "file://${FIRMWARE_sly} \
            file://${FIRMWARE_slim_line} \
            file://${FIRMWARE_wallsly} \
            file://${FIRMWARE_wallsly2} \
            file://${FIRMWARE_wallsly3} \
            file://${FIRMWARE_wallsly4} \
            file://${MAXTOUCH_INIT} \
	    file://touchcheck \
           "

inherit update-rc.d

INITSCRIPT_NAME = "${MAXTOUCH_INIT}"
INITSCRIPT_PARAMS = "start 06 S ."

do_compile[noexec] = "1"

do_configure[noexec] = "1"

do_compileconfigs[noexec] = "1"

do_setscene[noexec] = "1"

do_distribute_sources[noexec] = "1"

do_create_srcipk[noexec] = "1"

do_copy_license[noexec] = "1"

do_install() {
    install -d ${D}/${FIRMWARE_DIR}
     
    install -m 0644 ${WORKDIR}/${FIRMWARE_sly} ${D}/${FIRMWARE_DIR}
    install -m 0644 ${WORKDIR}/${FIRMWARE_wallsly} ${D}/${FIRMWARE_DIR}
    install -m 0644 ${WORKDIR}/${FIRMWARE_wallsly2} ${D}/${FIRMWARE_DIR}
    install -m 0644 ${WORKDIR}/${FIRMWARE_wallsly3} ${D}/${FIRMWARE_DIR}
    install -m 0644 ${WORKDIR}/${FIRMWARE_wallsly4} ${D}/${FIRMWARE_DIR}
    install -m 0644 ${WORKDIR}/${FIRMWARE_slim_line} ${D}/${FIRMWARE_DIR}

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/${MAXTOUCH_INIT} ${D}${sysconfdir}/init.d
    
    install -d ${D}/usr/local/bin
    install -m 0755 ${WORKDIR}/touchcheck ${D}/usr/local/bin
}

FILES_${PN} = "${FIRMWARE_DIR}/* ${sysconfdir}/init.d/${MAXTOUCH_INIT} /usr/local/bin/touchcheck"
