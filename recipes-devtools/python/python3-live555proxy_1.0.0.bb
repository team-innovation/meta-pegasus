DESCRIPTION = "Live555 Proxy Server Module for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "CLOSED"
SRCNAME = "live555-proxy-server"
PR = "ml66"

DEPENDS += "python3 live555"
#RDEPENDS = "live555-lib"
RDEPENDS_${PN} = "live555-libusageenvironment live555-libbasicusageenvironment live555-libgroupsock live555-liblivemedia"

SRCREV = "${GIT_LIVE555PROXY_REV}"
SRCBRANCH = "${GIT_LIVE555PROXY_BRANCH}"
GIT_LIVE555PROXY_SERVER ?= "${GIT_SERVER}"
GIT_LIVE555PROXY_PROTOCOL ?= "ssh"
MODULE = "live555-proxy-server"
SRC_URI = "git://${GIT_LIVE555PROXY_SERVER}/${MODULE};protocol=${GIT_LIVE555PROXY_PROTOCOL};branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit setuptools3 

