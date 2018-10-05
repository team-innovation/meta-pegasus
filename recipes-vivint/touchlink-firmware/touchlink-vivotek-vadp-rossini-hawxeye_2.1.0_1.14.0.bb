DESCRIPTION = "Vivotek VADP module - hawxeye1.14.0"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

require touchlink-vivotek-vadp-rossini.inc

PR = "r8"

VADP_VERSION = "2.2_1.15.3"
CODE_NAME = "hawxeye"
# using the PR in the name to stop the bitbake download cache
VADP_MODULE_NAME = "rossini-${CODE_NAME}-vadp_${VADP_VERSION}.tar.gz"

SRC_URI = "http://${DOWNLOAD_SERVER}/${VADP_MODULE_NAME}"
SRC_URI[md5sum] = "b8844e619771e23f3b50b80d59507b6b"
SRC_URI[sha256sum] = "240faf494874c769f93ab7d5c58987f5ae44ee86f7e8848c79fe6d968e2fde31"
