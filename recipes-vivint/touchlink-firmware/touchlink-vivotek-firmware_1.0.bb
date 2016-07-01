DESCRIPTION = "Vivotek V520IR video camera firmware"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"

PKGR_${PN}-520ir = "${PR}.45"
PKGR_${PN}-620pt = "${PR}.9"
PKGR_${PN}-cc8130 = "${PR}.3"
PKGR_${PN}-fd8134v = "${PR}.4"
PKGR_${PN}-fd8151v = "${PR}.5"
PKGR_${PN}-720w = "${PR}.5"
PKGR_${PN}-721w = "${PR}.13"
PKGR_${PN}-720 = "${PR}.2"
PKGR_${PN}-db8331w = "${PR}.26"
PKGR_${PN}-db8332w = "${PR}.13"

FIRMWARE_520IR = "IP8136W-ALAM-*.flash.pkg"
FIRMWARE_620PT = "PT8133-ALAM-*.flash.pkg"
FIRMWARE_CC8130 = "CC8130-VINT-*.flash.pkg"
FIRMWARE_FD8134V = "FD8134-VINT-*.flash.pkg"
FIRMWARE_FD8151V = "FD8151V-VINT-*.flash.pkg"
FIRMWARE_720W = "IP8333-VINT-*.flash.pkg"
FIRMWARE_721W = "IB8331W-VINT-*.flash.pkg"
FIRMWARE_720 = "IP8337-VINT-*.flash.pkg"
FIRMWARE_DB8331W = "DB8331W-VINT-*.flash.pkg"
FIRMWARE_DB8332W = "DB8332W-VINT-*.flash.pkg"

FIRMWARE_DIR = "/var/lib/firmware/Vivotek"

SRCREV = "${AUTOREV}"
SRC_URI = "hg://${HG_SERVER};module=camera-firmwares/Vivotek;protocol=http"
PV = "${SRCPV}"

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
     cp ${WORKDIR}/camera-firmwares/Vivotek/* ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-520ir = "${FIRMWARE_DIR}/${FIRMWARE_520IR}"
FILES_${PN}-620pt = "${FIRMWARE_DIR}/${FIRMWARE_620PT}"
FILES_${PN}-cc8130 = "${FIRMWARE_DIR}/${FIRMWARE_CC8130}"
FILES_${PN}-fd8134v = "${FIRMWARE_DIR}/${FIRMWARE_FD8134V}"
FILES_${PN}-fd8151v = "${FIRMWARE_DIR}/${FIRMWARE_FD8151V}"
FILES_${PN}-720w = "${FIRMWARE_DIR}/${FIRMWARE_720W}"
FILES_${PN}-721w = "${FIRMWARE_DIR}/${FIRMWARE_721W}"
FILES_${PN}-720 = "${FIRMWARE_DIR}/${FIRMWARE_720}"
FILES_${PN}-db8331w = "${FIRMWARE_DIR}/${FIRMWARE_DB8331W}"
FILES_${PN}-db8332w = "${FIRMWARE_DIR}/${FIRMWARE_DB8332W}"
FILES_${PN}-520ir-lic = "${FIRMWARE_DIR}/README"

PACKAGES = "${PN}-520ir \
			${PN}-520ir-lic\
            ${PN}-620pt \
            ${PN}-cc8130 \
            ${PN}-fd8134v \
            ${PN}-fd8151v \
            ${PN}-720w \
            ${PN}-721w \
            ${PN}-720 \
            ${PN}-db8331w \
            ${PN}-db8332w \
           "
