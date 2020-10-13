DESCRIPTION = "Bluetooth firmware"

SRC_URI += "file://vivint_blue.hex"

PR = "r5"

BLE_FW_HEX_NAME = "vivint_blue.hex"
BLE_FW_NAME = "vivint_blue.fw"

do_compile_append(){
	./ihex2fw ${BLE_FW_HEX_NAME} ${BLE_FW_NAME}
}

do_install_append () {
    install -m 644 ${S}/${BLE_FW_NAME} ${D}${FW_DIR}
}

FILES_${PN}-ble = "\
    ${FW_DIR}/${BLE_FW_NAME} \
    "

