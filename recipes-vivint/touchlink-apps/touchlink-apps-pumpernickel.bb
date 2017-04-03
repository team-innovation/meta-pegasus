# Copyright 2017 Vivint Inc.

DESCRIPTION = "User interface for Vivint sky panels"
HOMEPAGE = "http://www.vivintsky.com"
LICENSE = "CLOSED"
SECTION = ""
DEPENDS = "qtdeclarative qtgraphicaleffects"
PR = "r0"

HG_APPS_REV = "pumpernickel_diy"
# SRCREV = "596c72f5bb67f4dde3cbd6ec3b4bf8be8eb4ef5e"
SRCREV = "db050f5c6e59aecd1deee2c05742e50442605b8c"


SRC_URI = "hg://${HG_SERVER};module=${HG_APPS_TAG};proto=http"

S = "${WORKDIR}/apps-hg/code/pumpernickel"

require recipes-qt/qt5/qt5.inc

do_install() {
    install -d ${D}${datadir}/${P}
    install -m 0755 ${B}/pumpernickel ${D}${datadir}/${P}
    cp -a ${S}/content ${D}${datadir}/${P}
}

FILES_${PN}-dbg += "${datadir}/${P}/.debug"
FILES_${PN} += "${datadir}"

RDEPENDS_${PN} = "qtdeclarative-qmlplugins qtgraphicaleffects-qmlplugins"

#PACKAGE_ARCH = "${MACHINE_ARCH}"

#PACKAGES = "${PN}"
