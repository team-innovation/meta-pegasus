require psoc-fw.inc
GIT_ARTIFACTS_BRANCH ?= "develop"
GIT_ARTIFACTS_PROTOCOL ?= "ssh"
GIT_ARTIFACTS_SERVER ?= ${GIT_SERVER}
SRC_URI_append = "${GIT_ARTIFACTS_SERVER}/artifacts;protocol=${GIT_ARTIFACTS_PROTOCOL};branch=${GIT_ARTIFACTS_BRANCH}"
FW_NAME = "slimline-psoc5.fw"
FW_HEX_NAME = "git/slimline/LEDs.hex"
