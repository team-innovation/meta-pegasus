DESCRIPTION = "Vivotek VADP module - crimson"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

require touchlink-vivotek-vadp-rossini.inc

PR = "r1"

VADP_VERSION = "1.3"
CODE_NAME = "crimson"

SRC_URI = "http://${DOWNLOAD_SERVER}/rossini-odc-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

VADP_MODULE_NAME = "rossini-odc-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

SRC_URI[md5sum] = "199312853903f2b4f4782bf5ed6a53ed"
SRC_URI[sha256sum] = "8e832e8d435a24053b3dee91e0651fac82c9e8b4e3f77876a1773ab877d74818"

