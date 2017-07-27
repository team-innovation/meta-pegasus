DESCRIPTION = "Sierra HL7588 firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.sierrawireless.com"
PR = "r01"

PKGR_${PN}-hl7588-a = "${PR}.1"
PKGR_${PN}-hl7588-v = "${PR}.1"

FIRMWARE_hl7588_a = "RHL75xx.A.2.10.151600.201604151852.x7160_1_signed.fls"
FIRMWARE_hl7588_v = "SWIMCB71XX-VC4.12.00.171701.201705192050.01_signed.fls"

FIRMWARE_DIR = "/var/lib/firmware/Sierra"

SRC_URI = "file://${FIRMWARE_hl7588_a} \
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
     cp ${WORKDIR}/${FIRMWARE_hl7588_v} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-hl7588-a = "${FIRMWARE_DIR}/${FIRMWARE_hl7588_a}"
FILES_${PN}-hl7588-v = "${FIRMWARE_DIR}/${FIRMWARE_hl7588_v}"

PACKAGES = "${PN}-hl7588-a \
	    ${PN}-hl7588-v \
		   "
