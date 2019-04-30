DESCRIPTION = "Flash zwave firmware on first boot up"
HOMEPAGE = "http://www.vivint.com/"
SECTION = "base"
LICENSE = "CLOSED"
PR = "r3"
PV = "1.0+git${SRCPV}"

RDEPENDS_${PN} = " \
    touchlink-apps \
    python3-intelhex \
    python3-pyserial \
"

SRCREV = "${AUTOREV}"

SRC_URI = " \
    git://git@source.vivint.com:7999/em/z-wave;protocol=ssh;branch=develop \
    file://zwave-program \
    "

inherit update-rc.d

INITSCRIPT_NAME = "zwave-program"
INITSCRIPT_PARAMS = "start 08 S."


FW_NAME = "serialapi_controller_bridge_ZW050x_US.hex"
FW_DIR = "/lib/firmware/vivint"

S = "${WORKDIR}/git/SDK_v6_71_01/ProductPlus/SerialAPIPlus/build_prj/serialapi_controller_bridge_ZW050x_US"


do_compile[noexec] = "1"

do_runstrip[noexec] = "1"

do_install () {
    install -d ${D}${FW_DIR}
    install -m 644 ${S}/${FW_NAME} ${D}${FW_DIR}
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/zwave-program ${D}${sysconfdir}/init.d
}

FILES_${PN} = "\
    ${FW_DIR}/${FW_NAME} \
    ${sysconfdir}/init.d/zwave-program \
    ${sysconfdir}/rcS.d/*zwave-program \
    "
