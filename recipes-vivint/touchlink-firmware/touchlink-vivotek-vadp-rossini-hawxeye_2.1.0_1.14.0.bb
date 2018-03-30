DESCRIPTION = "Vivotek VADP module - hawxeye1.14.0"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

require touchlink-vivotek-vadp-rossini.inc

PR = "r4"

VADP_VERSION = "2.1_1.14.0"
CODE_NAME = "hawxeye"
# using the PR in the name to stop the bitbake download cache
VADP_MODULE_NAME = "rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

SRC_URI = "http://${DOWNLOAD_SERVER}/rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz?cachebreak=${PR}"
SRC_URI[md5sum] = "19fbd4f3544e654008f628fbd10de005"
SRC_URI[sha256sum] = "3efb31988a1c30c31e71e6cf3c3ba5a78827864fe4fd4bc0d9e62b3ab742c3b0"
