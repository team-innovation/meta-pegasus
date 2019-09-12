SUMMARY = "Zware SDK"
HOMEPAGE = "http://zts.sigmadesigns.com"
SECTION = "network"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3ac958de15d720d5bb5067dd818b8eae"

DEPENDS = "python-native openssl"

PR = "r2"
PV = "1.20+git${SRCPV}"

SRCREV = "3d9a65b72d54beb67a446bdaf5d2ecda91c4ae04"
SRCBRANCH = "master"

GIT_ZWARE_SERVER ?= "${GIT_SERVER}"
GIT_ZWARE_PROTOCOL ?= "ssh"

SRC_URI = "git://${GIT_ZWARE_SERVER}/zware_controller_sdk;protocol=${GIT_ZWARE_PROTOCOL};branch=${SRCBRANCH}"

S = "${WORKDIR}/git/zware_1_20/src/zwave/hcapi"

do_compile() {
    cd ${S}
    oe_runmake TARGET_PLATFORM=VIVINT_SLY_DEBUG
}

do_install_append() {
    install -d ${D}${includedir}/zip
    cp -r ${S}/include ${D}${includedir}/zip/include
    install -d ${D}${libdir}
    install -m 644 ${S}/lib/libzip_api.a ${D}${libdir}/libzip_api.a
    install -m 644 ${S}/src/libzip_ctl.a ${D}${libdir}/libzip_ctl.a
}

FILES_${PN} += "${libdir}/libzip_api.a"
FILES_${PN} += "${libdir}/libzip_ctl.a"

RPROVIDES_${PN} = "zware"

