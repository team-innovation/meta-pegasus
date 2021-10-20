DESCRIPTION = "Quectel EG91 firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.quectel.com"
PR = "r04"

PKGR_${PN}-eg91-f = "${PR}.1"
PKGR_${PN}-eg91-version = "${PR}.1"

# the build system doesn't like zip files, so the file has a ".hex" extension added
# that will need to be removed when we move the file into it's final location
FIRMWARE_eg91_f = "FOTA_EG91NAFBR05A04.V04-R05A05.V09.zip"
FIRMWARE_eg91_fh = "FOTA_EG91NAFBR05A04.V04-R05A05.V09.zip.hex"
# the version value must be the value read by AT+QGMR for the current firmware image
FIRMWARE_eg91_old_version = "EG91NAFBR05A04M4G_01.004.01.004-V04"
FIRMWARE_eg91_version = "EG91NAFBR05A05M4G_01.005.01.005-V09"

FIRMWARE_DIR = "/var/lib/firmware/Quectel"

SRC_URI = "file://${FIRMWARE_eg91_fh} \
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
     cp ${WORKDIR}/${FIRMWARE_eg91_fh} ${D}/${FIRMWARE_DIR}/${FIRMWARE_eg91_f}
     echo ${FIRMWARE_eg91_old_version}","${FIRMWARE_eg91_version}","${FIRMWARE_eg91_f} > ${D}/${FIRMWARE_DIR}/version
}

FILES_${PN}-eg91-f = "${FIRMWARE_DIR}/${FIRMWARE_eg91_f}"
FILES_${PN}-eg91-version = "${FIRMWARE_DIR}/version"

PACKAGES = "${PN}-eg91-f \
            ${PN}-eg91-version \
           "
