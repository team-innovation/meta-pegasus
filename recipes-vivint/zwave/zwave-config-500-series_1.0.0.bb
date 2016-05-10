DESCRIPTION = "Flash zwave firmware on first boot up"
HOMEPAGE = "http://www.vivint.com/"
SECTION = "base"
LICENSE = "CLOSED"
PR = "r1"

RDEPENDS_${PN} = " \
    touchlink-apps \
    python3-intelhex \
    python3-pyserial \
"

SRCREV = "${AUTOREV}"

SRC_URI = " \
	git://git@git.vivint.com/~/Z-Wave_SDK_06.51.06;protocol=ssh \
    file://zwave-program \
	"

FW_NAME = "serialapi_controller_static_ZW050x_US.hex"
FW_DIR = "/lib/firmware/vivint"

S = "${WORKDIR}/git/SDK/Product/SerialAPI/build_prj/serialapi_controller_static_ZW050x_US/Rels"


do_compile() {
	:
}

do_runstrip() {
        :
}

do_install () {
    install -d ${D}${FW_DIR}
    install -m 644 ${S}/${FW_NAME} ${D}${FW_DIR}
    install -d ${D}${sysconfdir}/zwave
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/zwave-program ${D}${sysconfdir}/init.d

    # Create runlevel links
    update-rc.d -r ${D} zwave-program start 07 S .
}

FILES_${PN} = "\
    ${FW_DIR}/${FW_NAME} \
    ${sysconfdir}/init.d/zwave-program \
    ${sysconfdir}/rcS.d/*zwave-program \
    ${sysconfdir}/zwave \
    "
