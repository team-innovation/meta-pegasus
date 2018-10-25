DESCRIPTION = "Flash zwave firmware on first boot up"
HOMEPAGE = "http://www.vivint.com/"
SECTION = "base"
LICENSE = "CLOSED"
PR = "r3"
#PV = "${SRCPV}"

RDEPENDS_${PN} = " \
    python3-intelhex \
    python3-pyserial \
"

# TODO uncomment this section
#GIT_ARTIFACTS_BRANCH ?= "develop"
#GIT_ARTIFACTS_PROTOCOL ?= "ssh"
#GIT_ARTIFACTS_SERVER ?= "${GIT_SERVER}"
#GIT_ARTIFACTS_REV = "${AUTOREV}"
#SRCREV = "${GIT_ARTIFACTS_REV}"
#
#SRC_URI = " \
#    git://${GIT_ARTIFACTS_SERVER}/artifacts;protocol=${GIT_ARTIFACTS_PROTOCOL};branch=${GIT_ARTIFACTS_BRANCH} \
#    file://zwave-program \
#    "

# NOTICE This should only be in feature branch
SRC_URI = " \
    file://serialapi_controller_bridge_ZW050x_US.hex \
    file://zwave-program \
    "
# End notice

FW_NAME = "serialapi_controller_bridge_ZW050x_US.hex"
#FCC_FW_NAME = "micro_rf_linkX_ZW050x_ALL.hex"
FW_DIR = "/lib/firmware/vivint"

# TODO uncomment this section
#S = "${WORKDIR}/git/wallsly"

# NOTICE This should only be in feature branch
S = "${WORKDIR}"
# End notice

do_compile() {
    :
}

do_runstrip() {
    :
}

do_install () {
    install -d ${D}${FW_DIR}
    install -m 644 ${S}/${FW_NAME} ${D}${FW_DIR}
    #install -m 644 ${S}/${FCC_FW_NAME} ${D}${FW_DIR}
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/zwave-program ${D}${sysconfdir}/init.d

    # Create runlevel links
    update-rc.d -r ${D} zwave-program start 08 S .
}

FILES_${PN} = "\
    ${FW_DIR}/${FW_NAME} \
    ${sysconfdir}/init.d/zwave-program \
    ${sysconfdir}/rcS.d/*zwave-program \
    "
#FILES_${PN} = "\
#    ${FW_DIR}/${FW_NAME} \
#    ${FW_DIR}/${FCC_FW_NAME} \
#    ${sysconfdir}/init.d/zwave-program \
#    ${sysconfdir}/rcS.d/*zwave-program \
#    "
