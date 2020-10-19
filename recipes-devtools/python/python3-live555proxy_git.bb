SUMMARY = "Live555 Proxy Server Module for Python"
HOMEPAGE = "https://source.vivint.com/projects/EM/repos/live555-proxy-server/browse"
SECTION = "devel/python"
LICENSE = "CLOSED"
SRCNAME = "live555-proxy-server"

DEPENDS += "live555"

SRCREV = "${GIT_LIVE555PROXY_REV}"
SRCBRANCH = "${GIT_LIVE555PROXY_BRANCH}"
GIT_LIVE555PROXY_SERVER ?= "${GIT_SERVER}"
GIT_LIVE555PROXY_PROTOCOL ?= "ssh"
MODULE = "live555-proxy-server"
SRC_URI = "git://${GIT_LIVE555PROXY_SERVER}/${MODULE};protocol=${GIT_LIVE555PROXY_PROTOCOL};branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

inherit setuptools3 

RDEPENDS_${PN} = "live555-libusageenvironment live555-libbasicusageenvironment live555-libgroupsock live555-liblivemedia"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
