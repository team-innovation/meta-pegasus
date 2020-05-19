DESCRIPTION = "Sierra HL7588 firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.sierrawireless.com"
PR = "r02"

PKGR_${PN}-hl7588-a = "${PR}.1"
PKGR_${PN}-hl7588-v = "${PR}.1"

FIRMWARE_hl7588_a = "RHL75xx.A.2.15.151600.201809201422.x7160_3_signed.fls"
FIRMWARE_hl7588_v = "SWIMCB71XX-VC4.31.02.180100.201807131739.01_signed.fls"

FIRMWARE_DIR = "/var/lib/firmware/Sierra"

SRC_URI = "file://${FIRMWARE_hl7588_a} \
	       file://${FIRMWARE_hl7588_v} \
		  "

do_compile[noexec] = "1"

do_configure[noexec] = "1"

do_compileconfigs[noexec] = "1"

do_setscene[noexec] = "1"

do_distribute_sources[noexec] = "1"

do_create_srcipk[noexec] = "1"

do_copy_license[noexec] = "1"

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
