require openwrt.inc

GIT_ARTIFACTS_BRANCH ?= "develop"
GIT_ARTIFACTS_PROTOCOL ?= "ssh"

GIT_ARTIFACTS_REV ?= "${AUTOREV}"
SRCREV = "${GIT_ARTIFACTS_REV}"

SRC_URI_append = "git://git@source.vivint.com:7999/em/artifacts;protocol=${GIT_ARTIFACTS_PROTOCOL};branch=${GIT_ARTIFACTS_BRANCH}"

PACKAGES = "${PN}-rt3352 \
            ${PN}-mt7620 \
	    "
