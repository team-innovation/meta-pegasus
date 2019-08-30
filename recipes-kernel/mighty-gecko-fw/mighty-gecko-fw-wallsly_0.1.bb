require mighty-gecko-fw.inc

GIT_ARTIFACTS_BRANCH ?= "develop"
GIT_ARTIFACTS_SERVER ?= "${GIT_SERVER}"
GIT_ARTIFACTS_PROTOCOL ?= "ssh"
SRC_URI_append = "git://${GIT_ARTIFACTS_SERVER}/artifacts;protocol=${GIT_ARTIFACTS_PROTOCOL};branch=${GIT_ARTIFACTS_BRANCH}"

SINK_FW_NAME = "wallsly-mighty-gecko.fw"
SINK_FW_FCC_NAME = "railtest_efr32.fw"
SINK_FW_HEX_NAME = "git/wallsly/panel_sink.hex"
SINK_FW_FCC_HEX_NAME = "git/wallsly/railtest_efr32.hex"

SINK_FLEX_FW_NAME = "wallsly-flex-gecko.fw"
SINK_FLEX_FW_FCC_NAME = "railtest_efr32_flex1_uart.fw"
SINK_FLEX_FW_HEX_NAME = "git/wallsly/panel_sink_flex1_uart.hex"
SINK_FLEX_FW_FCC_HEX_NAME = "git/wallsly/railtest_efr32_flex1_uart.hex"
