DESCRIPTION = "Vivotek VADP module - hawxeye1.14.0"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

require touchlink-vivotek-vadp-rossini.inc

PR = "r10"

VADP_VERSION = "2.4_1.15.3"
CODE_NAME = "hawxeye"
# using the PR in the name to stop the bitbake download cache
VADP_MODULE_NAME = "rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

SRC_URI = "http://${DOWNLOAD_SERVER}/${VADP_MODULE_NAME}"
SRC_URI[md5sum] = "d2604214b02d531a03d243ec03837efc"
SRC_URI[sha256sum] = "721788b25fdf20166fed239f84cc1998e95514f0c5380c7e126885fad4bc3aa2"

