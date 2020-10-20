DESCRIPTION = "Bluetooth rail test firmware"

PR = "r2"

BLE_FW_HEX_NAME = "railtest_blue.hex"
BLE_FW_NAME = "railtest_blue.fw"

SRC_URI += "file://${BLE_FW_HEX_NAME}"

do_compile_append(){
	./ihex2fw ${BLE_FW_HEX_NAME} ${BLE_FW_NAME}
}

do_install_append () {
    install -m 644 ${S}/${BLE_FW_NAME} ${D}${FW_DIR}
}

FILES_${PN}-ble = "\
    ${FW_DIR}/${BLE_FW_NAME} \
    "

