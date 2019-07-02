DESCRIPTION = "LG PoE wifi bridge firmware"

POE_BRIDGE_FIRMWARE = "SDK_v1.51_r218_201806271.bin"
SRC_URI += "http://${UPDATESENG}/camera_firmwares/LG/${POE_BRIDGE_FIRMWARE};name=poe"
PR = "r1"

SRC_URI[poe.md5sum] = "186d60727e8499b43c17ac6ef98ffbfa"
SRC_URI[poe.sha256sum] = "029c484ec31de1000599e70f086f077b8bc37545f5c2110024d05eec221ebd5f"

do_install_append() {
     cp ${WORKDIR}/${POE_BRIDGE_FIRMWARE} ${D}/${FIRMWARE_DIR}
}

FILES_${PN}-poe-bridge = "${FIRMWARE_DIR}/${POE_BRIDGE_FIRMWARE}"

