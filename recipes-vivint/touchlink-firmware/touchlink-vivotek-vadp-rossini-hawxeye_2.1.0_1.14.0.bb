DESCRIPTION = "Vivotek VADP module - hawxeye1.14.0"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

require touchlink-vivotek-vadp-rossini.inc

PR = "r7"

VADP_VERSION = "2.2_1.15.3"
CODE_NAME = "hawxeye"
# using the PR in the name to stop the bitbake download cache
VADP_MODULE_NAME = "rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

SRC_URI = "http://${DOWNLOAD_SERVER}/${VADP_MODULE_NAME}"
SRC_URI[md5sum] = "555de0a8ba6d8f01b4cab2f1a91d908c"
SRC_URI[sha256sum] = "5013ea23496e84052b5e9c30eae02096f5fb94b80d578758e87e291fb2c8b4cb"
