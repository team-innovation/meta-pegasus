DESCRIPTION = "Vivotek VADP module - sienna"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

PR = "r2"

require touchlink-vivotek-vadp-rossini.inc

VADP_VERSION = "1.1"
CODE_NAME = "sienna"

SRC_URI = "http://${DOWNLOAD_SERVER}/rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

SRC_URI[md5sum] = "9ff541cc57a2e3dfae0a957d4f9be289"
SRC_URI[sha256sum] = "3feaec7d3de69042ed7a58b4bf03947bcff45e8ffb480891c73f15719e5f5844"

VADP_MODULE_NAME = "rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

do_compile_prepend() {
     :
}
