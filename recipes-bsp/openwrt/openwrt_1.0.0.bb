require openwrt.inc
GIT_ARTIFACTS_BRANCH ?= "develop"
GIT_ARTIFACTS_SERVER ?= "${GIT_SERVER}"
GIT_ARTIFACTS_PROTOCOL ?= "ssh"

GIT_ARTIFACTS_REV ?= "${AUTOREV}"
SRCREV = "${GIT_ARTIFACTS_REV}"

SRC_URI_append = "git://${GIT_ARTIFACTS_SERVER}/artifacts;protocol=${GIT_ARTIFACTS_PROTOCOL};branch=${GIT_ARTIFACTS_BRANCH} \
		  file://networkmodule \
		  file://COPYING"
inherit update-rc.d

INITSCRIPT_NAME = "networkmodule"
INITSCRIPT_PARAMS = "stop 16 0 6 ."

PACKAGES = "${PN}-rt3352 \
            ${PN}-mt7620 \
	    "
