DESCRIPTION = "Vivotek VADP module - crimson"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

require touchlink-vivotek-vadp-rossini.inc

VADP_VERSION = "1.0"
CODE_NAME = "crimson"

SRC_URI = "http://${DOWNLOAD_SERVER}/rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

VADP_MODULE_NAME = "rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

SRC_URI[md5sum] = "36698ab7032df08fc232412a266298cc"
SRC_URI[sha256sum] = "219d1433f7a6f409d1fedc42e4e7a0575fd5e1698d39be1df9de47e640f0f574"
