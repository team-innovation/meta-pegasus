DESCRIPTION = "OpenWrt 17.02 version for MT7620 Network Module"

require openwrt.inc

GIT_ARTIFACTS_BRANCH ?= "develop"
GIT_ARTIFACTS_SERVER ?= "${GIT_SERVER}"
GIT_ARTIFACTS_PROTOCOL ?= "ssh"

GIT_ARTIFACTS_REV ?= "${AUTOREV}"
SRCREV = "${GIT_ARTIFACTS_REV}"

PR = "r2"
PV = "${SRCPV}"

SRC_URI_append = "git://${GIT_ARTIFACTS_SERVER}/artifacts;protocol=${GIT_ARTIFACTS_PROTOCOL};branch=${GIT_ARTIFACTS_BRANCH} \
		  file://networkmodule"

FW_DIR = "/lib/firmware/legacy"
