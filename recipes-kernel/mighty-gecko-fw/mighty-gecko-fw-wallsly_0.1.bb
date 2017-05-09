require mighty-gecko-fw.inc

SRC_URI_append = "git://git.vivint.com/sensor-connect;branch=master "
FW_NAME = "railtest_efr32.fw"
FW_HEX_NAME = "git/strategy/railtest_efr32/GNU\ ARM\ v4.9.3\ -\ Debug/railtest_efr32.hex"

SINK_FW_NAME = "wallsly-mighty-gecko.fw"
SINK_FW_HEX_NAME = "git/strategy/sink_6/GNU\ ARM\ v4.9.3\ -\ Debug\ -\ EFR32MG1P233F256GM48/sink_6.hex"
