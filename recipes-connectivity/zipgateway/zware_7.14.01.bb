SUMMARY = "Zware SDK"
HOMEPAGE = "http://zts.sigmadesigns.com"
SECTION = "network"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3ac958de15d720d5bb5067dd818b8eae"

DEPENDS = "python-native openssl openssl11"

PR = "r1"
PV = "7.14.01+git${SRCPV}"

SRCREV = "eb124bd1868e2a2b82180485c5e66a3ed2e96a08"
SRCBRANCH = "v7.14.1"

GIT_ZWARE_SERVER ?= "${GIT_SERVER}"
GIT_ZWARE_PROTOCOL ?= "ssh"

SRC_URI = " \
	git://${GIT_ZWARE_SERVER}/zware_controller_sdk;protocol=${GIT_ZWARE_PROTOCOL};branch=${SRCBRANCH} \
	file://zware.pc \
	"
S = "${WORKDIR}/git/zware-7.14.01/src/zwave/hcapi"

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

    install -d ${D}${libdir}/pkgconfig
    install -m 644 ${WORKDIR}/zware.pc ${D}${libdir}/pkgconfig
}

FILES_${PN} += "${libdir}/libzip_api.a"
FILES_${PN} += "${libdir}/libzip_ctl.a"

RPROVIDES_${PN} = "zware"

