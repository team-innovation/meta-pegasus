LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

DOWNLOAD_SERVER="updateseng.vivint.com/innovation/analytics"

FIRMWARE_DIR = "/var/lib/firmware/Vivotek"

do_unpack() {
	cp ${DL_DIR}/${VADP_MODULE_NAME} ${WORKDIR}
}

do_patch() {
	:
}

do_configure() {
	:
}

do_compile() {
	:
}

do_install() {
     install -d ${D}/${FIRMWARE_DIR}
     cp ${WORKDIR}/${VADP_MODULE_NAME} ${D}/${FIRMWARE_DIR}
}
