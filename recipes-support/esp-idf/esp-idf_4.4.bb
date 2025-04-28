SUMMARY = "ESP-Hosted solution provides a way to provision ESP board"
HOMEPAGE = "https://github.com/espressif/esp-idf/"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = "git://github.com/espressif/esp-idf.git;protocol=https;branch=release/v4.4"
SRCREV = "e9d442d2b721293497a3a0bcfb45883a7c5111b9"

DEPENDS += "\
    protobuf-native \
"

RDEPENDS:${PN} += "\
    python3-cryptography \
    python3-protobuf \
    python3-future \
    python3-core \
"

S="${WORKDIR}/git"

inherit python3-dir

do_compile () {
    cd ${S}/components/protocomm/proto
    protoc --python_out=../python -I . *.proto

    cd ${S}/components/wifi_provisioning/proto
    protoc --python_out=../python -I . -I ../../protocomm/proto/ *.proto

    cd ${S}/examples/provisioning/legacy/custom_config/components/custom_provisioning/proto
    protoc --python_out=../python *.proto
}

do_configure[noexec] = "1"

do_install () {
    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/
    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/security
    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/transport
    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/utils
    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/prov
    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/proto
    install -m 0644 ${S}/tools/esp_prov/security/* ${D}${libdir}/${PYTHON_DIR}/site-packages/security
    install -m 0644 ${S}/tools/esp_prov/transport/* ${D}${libdir}/${PYTHON_DIR}/site-packages/transport
    install -m 0644 ${S}/tools/esp_prov/utils/* ${D}${libdir}/${PYTHON_DIR}/site-packages/utils
    install -m 0644 ${S}/tools/esp_prov/prov/* ${D}${libdir}/${PYTHON_DIR}/site-packages/prov
    install -m 0644 ${S}/tools/esp_prov/proto/* ${D}${libdir}/${PYTHON_DIR}/site-packages/proto

    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/proto/components/protocomm/python
    install -m 0644 ${S}/components/protocomm/python/* ${D}${libdir}/${PYTHON_DIR}/site-packages/proto/components/protocomm/python

    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/proto/components/wifi_provisioning/python
    install -m 0644 ${S}/components/wifi_provisioning/python/* ${D}${libdir}/${PYTHON_DIR}/site-packages/proto/components/wifi_provisioning/python

    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/proto/examples/provisioning/legacy/custom_config/components/custom_provisioning/python
    install -m 0644 ${S}/examples/provisioning/legacy/custom_config/components/custom_provisioning/python/custom_config_pb2.py ${D}${libdir}/${PYTHON_DIR}/site-packages/proto/examples/provisioning/legacy/custom_config/components/custom_provisioning/python

    sed -i "s|idf_path = os.environ.*|idf_path = '\\/${libdir}\\/${PYTHON_DIR}\\/site-packages\\/proto/'|" ${D}${libdir}/${PYTHON_DIR}/site-packages/proto/__init__.py
}

FILES:${PN} += "${libdir}/${PYTHON_DIR}/site-packages/*"
