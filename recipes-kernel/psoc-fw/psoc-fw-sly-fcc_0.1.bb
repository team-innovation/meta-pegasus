require psoc-fw.inc


GIT_ARTIFACTS_BRANCH ?= "develop"
SRC_URI_append = "git://git@source.vivint.com:7999/em/artifacts;protocol=ssh;branch=${GIT_ARTIFACTS_BRANCH}"
FW_NAME = "sly-fcc-psoc5.fw"
FW_HEX_NAME = "git/FCC/sly/LEDs.hex"
