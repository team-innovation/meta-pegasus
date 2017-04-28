DESCRIPTION = "Flash zwave firmware on first boot up"
HOMEPAGE = "http://www.vivint.com/"
SECTION = "base"
LICENSE = "CLOSED"
PR = "r3"

RDEPENDS_${PN} = " \
    touchlink-apps \
    python3-intelhex \
    python3-pyserial \
"

SRCREV = "630f3390118d35f9794569e4c3fbd011cdcfe112"

SRC_URI = " \
    git://git@git.vivint.com/~/Z-Wave_SDK_06.70.01;protocol=ssh \
    file://zwave-program \
    "

FW_NAME = "serialapi_controller_static_ZW050x_US.hex"
FW_NAME_FCC = "micro_rf_linkX_ZW050x_ALL.hex"
FW_DIR = "/lib/firmware/vivint"

S = "${WORKDIR}/git/SDK_v6_70_01/ProductPlus/SerialAPIPlus/build_prj/serialapi_controller_static_ZW050x_US"
S_FCC = "${WORKDIR}/git/SDK_v6_70_01/ProductPlus/SerialAPIPlus/micro_rf_linkx_v1.19/Micro_RF_LinkX"


do_compile() {
    :
}

do_runstrip() {
    :
}

do_install () {
    install -d ${D}${FW_DIR}
    install -m 644 ${S}/${FW_NAME} ${D}${FW_DIR}
    install -m 644 ${S_FCC}/${FW_NAME_FCC} ${D}${FW_DIR}
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/zwave-program ${D}${sysconfdir}/init.d

    # Create runlevel links
    update-rc.d -r ${D} zwave-program start 07 S .
}

FILES_${PN} = "\
    ${FW_DIR}/${FW_NAME} \
    ${FW_DIR}/${FW_NAME_FCC} \
    ${sysconfdir}/init.d/zwave-program \
    ${sysconfdir}/rcS.d/*zwave-program \
    "
