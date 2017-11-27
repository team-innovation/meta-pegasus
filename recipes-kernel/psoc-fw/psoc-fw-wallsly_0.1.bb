require psoc-fw.inc

GIT_ARTIFACTS_BRANCH ?= "develop"
GIT_ARTIFACTS_SERVER ?= "${GIT_SERVER}"
GIT_ARTIFACTS_PROTOCOL ?= "ssh"
SRC_URI_append = "${GIT_ARTIFACTS_SERVER}/artifacts;protocol=${GIT_ARTIFACTS_PROTOCOL};branch=${GIT_ARTIFACTS_BRANCH}"

FW_NAME = "wallsly-psoc5.fw"
FW_HEX_NAME = "git/wallsly/LEDs.hex"
