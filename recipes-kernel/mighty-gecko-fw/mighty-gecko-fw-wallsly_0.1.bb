require mighty-gecko-fw.inc

SRC_URI_append = "git://git@source.vivint.com:7999/em/artifacts;protocol=ssh;branch=develop"
FW_NAME = "railtest_efr32.fw"
FW_HEX_NAME = "git/strategy/railtest_efr32/GNU\ ARM\ v4.9.3\ -\ Debug/railtest_efr32.hex"

SINK_FW_NAME = "wallsly-mighty-gecko.fw"
SINK_FW_HEX_NAME = "git/wallsly/panel_sink.hex"
