DESCRIPTION = "Telit HE910 firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.telit.com"
PR = "ml2"

PKGR_${PN}-he910 = "${PR}.1"
PKGR_${PN}-ue910 = "${PR}.1"

FIRMWARE_he910 = "stream_HE910_NAR-12.00.304.bin"
FIRMWARE_ue910 = "stream_UE910_NAR-12.00.504.bin"

FIRMWARE_DIR = "/var/lib/firmware/Telit"

SRC_URI = "file://${FIRMWARE_he910} \
	   file://${FIRMWARE_ue910} \
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
     cp ${WORKDIR}/${FIRMWARE_he910} ${D}/${FIRMWARE_DIR}
     cp ${WORKDIR}/${FIRMWARE_ue910} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-he910 = "${FIRMWARE_DIR}/${FIRMWARE_he910}"
FILES_${PN}-ue910 = "${FIRMWARE_DIR}/${FIRMWARE_ue910}"

PACKAGES = "${PN}-he910 \
	    ${PN}-ue910 \
		   "
