SUMMARY = "Zwave NVM Converter"
HOMEPAGE = "http://zts.sigmadesigns.com"
SECTION = "network"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://../zware-7.13.01/LICENSE;md5=3ac958de15d720d5bb5067dd818b8eae"

DEPENDS = ""

PR = "r1"
PV = "6.71+git${SRCPV}"

SRCREV = "a82d0c23a52adb5cbe97a40d0fa971d7534a85b0"
SRCBRANCH = "v7.13.1"

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

