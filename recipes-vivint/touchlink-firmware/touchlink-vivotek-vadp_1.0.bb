DESCRIPTION = "Vivotek VADP module"
LICENSE = "Closed"
HOMEPAGE = "http://www.vivint.com"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

require touchlink-vivotek-vadp.inc

VADP_MODULE_NAME = "dbc-vadp_${VADP_VERSION}.tar.gz"

do_compile_prepend() {
     source ./SOURCEME-DM36x
}

FILES_${PN} = "${FIRMWARE_DIR}/${VADP_MODULE_NAME}"
