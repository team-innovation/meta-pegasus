SUMMARY = "Zwave NVM Converter"
HOMEPAGE = "http://zts.sigmadesigns.com"
SECTION = "network"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://../zipgateway-7.15.04-Source/usr/local/LICENSE;md5=d8940d2702ac72abe9e3994316807ff3"

DEPENDS = ""

PR = "r1"
PV = "6.71+git${SRCPV}"

#SRCREV = "64e75bb5596062d7fac742f677122537f34002a7"
SRCREV = "${AUTOREV}"
SRCBRANCH = "v7.15.4_wip"

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

