DESCRIPTION = "Sierra HL7588 firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.sierrawireless.com"
PR = "r03"

PKGR_${PN}-hl7588-a = "${PR}.1"
PKGR_${PN}-hl7588-3g = "${PR}.1"
PKGR_${PN}-hl7588-v = "${PR}.1"

FIRMWARE_hl7588_a = "SWIMCB71XX-AIM1.04.02.A18.164804.201810182055.02_signed.fls"
FIRMWARE_hl7588_3g = "RHL75xx.A.2.15.151600.201809201422.x7160_3_signed.fls"
FIRMWARE_hl7588_v = "SWIMCB71XX-VC4.31.02.180100.201807131739.01_signed.fls"

FIRMWARE_DIR = "/var/lib/firmware/Sierra"

SRC_URI = "file://${FIRMWARE_hl7588_a} \
	   file://${FIRMWARE_hl7588_3g} \
	   file://${FIRMWARE_hl7588_v} \
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
     cp ${WORKDIR}/${FIRMWARE_hl7588_a} ${D}/${FIRMWARE_DIR}
     cp ${WORKDIR}/${FIRMWARE_hl7588_3g} ${D}/${FIRMWARE_DIR}
     cp ${WORKDIR}/${FIRMWARE_hl7588_v} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-hl7588-a = "${FIRMWARE_DIR}/${FIRMWARE_hl7588_a}"
FILES_${PN}-hl7588-3g = "${FIRMWARE_DIR}/${FIRMWARE_hl7588_3g}"
FILES_${PN}-hl7588-v = "${FIRMWARE_DIR}/${FIRMWARE_hl7588_v}"

PACKAGES = "${PN}-hl7588-a \
	    ${PN}-hl7588-3g \
	    ${PN}-hl7588-v \
	   "
