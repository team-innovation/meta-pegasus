DESCRIPTION = "Vivotek VADP module - sienna"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

require touchlink-vivotek-vadp-rossini.inc

VADP_VERSION = "1.0"
CODE_NAME = "sienna"

SRC_URI = "http://${DOWNLOAD_SERVER}/rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"


SRC_URI[md5sum] = "a5239b2188bbeba72e7accd7aca20731"
SRC_URI[sha256sum] = "89e70ae4068574d9c70f5fd7043c59a5920bd524f6fb3879559234606a24d0f9"

VADP_MODULE_NAME = "rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

do_compile_prepend() {
     :
}
