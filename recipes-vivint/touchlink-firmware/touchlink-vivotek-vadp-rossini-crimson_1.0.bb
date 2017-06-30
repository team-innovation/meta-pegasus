DESCRIPTION = "Vivotek VADP module - crimson"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

require touchlink-vivotek-vadp-rossini.inc

PR = "r1"

VADP_VERSION = "1.1"
CODE_NAME = "crimson"

SRC_URI = "http://${DOWNLOAD_SERVER}/rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

VADP_MODULE_NAME = "rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

SRC_URI[md5sum] = "efe46686c37653610356e0e561a3e907"
SRC_URI[sha256sum] = "2e468ab298e9299b937f4ddc870f56d165446c1a0888c3fb94e4cfb37b5f56c5"
