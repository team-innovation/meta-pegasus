DESCRIPTION = "Vivotek VADP module - hawxeye1.14.0"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

require touchlink-vivotek-vadp-rossini.inc

PR = "r6"

VADP_VERSION = "2.1_1.14.0"
CODE_NAME = "hawxeye"
# using the PR in the name to stop the bitbake download cache
VADP_MODULE_NAME = "rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

SRC_URI = "http://${DOWNLOAD_SERVER}/${VADP_MODULE_NAME}"
SRC_URI[md5sum] = "755279caf7750da924e034157c69b4dd"
SRC_URI[sha256sum] = "a652df052acf707e8767efeea68b113274ecf87c62717c930a71e442b38a9fd9"
