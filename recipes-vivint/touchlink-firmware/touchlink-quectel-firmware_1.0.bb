DESCRIPTION = "Quectel EG91 firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.quectel.com"
PR = "r03"

PKGR_${PN}-eg91-f = "${PR}.1"

# the build system doesn't like zip files, so the file has a ".hex" extension added
# that will need to be removed when we move the file into it's final location
FIRMWARE_eg91_f = "FOTA_EG91NAFBR05A04_01.004.01.004-R05A05_01.005.01.005.zip"
FIRMWARE_eg91_fh = "FOTA_EG91NAFBR05A04_01.004.01.004-R05A05_01.005.01.005.zip.hex"

FIRMWARE_DIR = "/var/lib/firmware/Quectel"

SRC_URI = "file://${FIRMWARE_eg91_fh} \
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
     cp ${WORKDIR}/${FIRMWARE_eg91_fh} ${D}/${FIRMWARE_DIR}/${FIRMWARE_eg91_f}
}

FILES_${PN}-eg91-f = "${FIRMWARE_DIR}/${FIRMWARE_eg91_f}"

PACKAGES = "${PN}-eg91-f \
           "
