SUMMARY = "Zwave NVM Converter"
HOMEPAGE = "http://zts.sigmadesigns.com"
SECTION = "network"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3ac958de15d720d5bb5067dd818b8eae"

DEPENDS = ""

PR = "r1"
PV = "6.71+git${SRCPV}"

SRCREV = "ea48469163517f778101d6f76294863178a40973"
SRCBRANCH = "v7.14.1"

GIT_ZWAVE_SERVER ?= "${GIT_SERVER}"
GIT_ZWAVE_PROTOCOL ?= "ssh"

SRC_URI = "git://${GIT_ZWAVE_SERVER}/zware_controller_sdk;protocol=${GIT_ZWAVE_PROTOCOL};branch=${SRCBRANCH}"

S = "${WORKDIR}/git/zwave_nvm_converter"

inherit pkgconfig cmake

do_package_qa() {
    echo "Skipping QA ..."
}

do_install() {
    install -d ${D}${prefix}/local/sbin
    install -m 0755 ${WORKDIR}/build/ZWaveNVMConvert ${D}${prefix}/local/sbin
}

FILES_${PN} += "${prefix}/local/sbin/ZWaveNVMConvert"
FILES_${PN} += "${prefix}/local/sbin/.debug/ZWaveNVMConvert"

RPROVIDES_${PN} = "zwave-nvm-converter"

