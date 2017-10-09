require mighty-gecko-fw.inc

GIT_ARTIFACTS_BRANCH ?= "develop"

SRC_URI_append = "git://git@source.vivint.com:7999/em/artifacts;protocol=ssh;branch=${GIT_ARTIFACTS_BRANCH}"

SINK_FW_NAME = "wallsly-mighty-gecko.fw"
SINK_FW_HEX_NAME = "git/wallsly/panel_sink.hex"
