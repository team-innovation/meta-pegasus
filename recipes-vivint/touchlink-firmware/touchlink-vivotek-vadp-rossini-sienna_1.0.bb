DESCRIPTION = "Vivotek VADP module - sienna"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

PR = "r3"

require touchlink-vivotek-vadp-rossini.inc

VADP_VERSION = "1.2"
CODE_NAME = "sienna"

SRC_URI = "http://${DOWNLOAD_SERVER}/rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

SRC_URI[md5sum] = "8f15a34771ff64563f3dbe7de6b62d57"
SRC_URI[sha256sum] = "1121bbd1640f813b654a3a0695b7d258521dec7e7ab79c3419b011f683c8dc59"

VADP_MODULE_NAME = "rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

do_compile_prepend() {
     :
}
