SUMMARY = "Zware SDK"
HOMEPAGE = "http://zts.sigmadesigns.com"
SECTION = "network"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d8940d2702ac72abe9e3994316807ff3"

DEPENDS = "python-native openssl"

PR = "r2"
PV = "7.15.04+git${SRCPV}"

#SRCREV = "64e75bb5596062d7fac742f677122537f34002a7"
SRCREV = "${AUTOREV}"
SRCBRANCH = "v7.15.4_wip"

GIT_ZWARE_SERVER ?= "${GIT_SERVER}"
GIT_ZWARE_PROTOCOL ?= "ssh"

SRC_URI = " \
	git://${GIT_ZWARE_SERVER}/zware_controller_sdk;protocol=${GIT_ZWARE_PROTOCOL};branch=${SRCBRANCH} \
	file://zware.pc \
"

S = "${WORKDIR}/git/zware-7.15.04/src/zwave/hcapi"

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
FILES_${PN} += "${libdir}/pkgconfig/zware.pc"
ALLOW_EMPTY_${PN} = "1"

RPROVIDES_${PN} = "zware"

BBCLASSEXTEND = "native nativesdk"
