DESCRIPTION = "Vivotek VADP module - original omron"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

require touchlink-vivotek-vadp-rossini.inc

VADP_VERSION = "1.30"
CODE_NAME = "matsuura"

SRC_URI = "http://${DOWNLOAD_SERVER}/rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"


SRC_URI[md5sum] = "5f8262b36dcebca90da2417c4d3df254"
SRC_URI[sha256sum] = "80ff675a635b4602c43f651efca5b4efb4bfa81d412951c3089777948c3bff3a"

VADP_MODULE_NAME = "rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

do_compile_prepend() {
     :
}
