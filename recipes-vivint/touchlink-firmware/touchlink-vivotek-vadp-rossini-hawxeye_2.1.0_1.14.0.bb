DESCRIPTION = "Vivotek VADP module - hawxeye1.14.0"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

require touchlink-vivotek-vadp-rossini.inc

PR = "r9"

VADP_VERSION = "2.3_1.15.3"
CODE_NAME = "hawxeye"
# using the PR in the name to stop the bitbake download cache
VADP_MODULE_NAME = "rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

SRC_URI = "http://${DOWNLOAD_SERVER}/${VADP_MODULE_NAME}"
SRC_URI[md5sum] = "ee861e011a03ac4a43d1744d0cb454dc"
SRC_URI[sha256sum] = "349162be60a2c2fc87b4416191a7399efac26099de635a609d97d9009edf9ef9"
