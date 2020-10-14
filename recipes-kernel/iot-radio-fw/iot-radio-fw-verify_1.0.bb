SUMMARY = "Vivint Hub IOT Radio Firmware Verify Script"
DESCRIPTION = "This package provides s script to verify and reprogram a Vivint HUB IOT radio"
HOMEPAGE = "http://source.vivint.com"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=a6d576eb292a14a25860bf932896ef51"
PR = "r4"

SRC_URI = "file://iot-radio-fw-verify \
           file://COPYING"

inherit update-rc.d

INITSCRIPT_NAME = "iot-radio-fw-verify"
INITSCRIPT_PARAMS = "start 08 S ."


do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/iot-radio-fw-verify ${D}${sysconfdir}/init.d
}
