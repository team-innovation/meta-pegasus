# Copyright 2017 Vivint Inc.

DESCRIPTION = "User interface for Vivint sky panels"
HOMEPAGE = "http://www.vivintsky.com"
LICENSE = "CLOSED"
SECTION = "SOMETHING"
DEPENDS = "qtdeclarative qtgraphicaleffects"
PR = "r2"

SRCREV = "${AUTOREV}"

SRC_URI = "hg://${HG_SERVER};module=${HG_APPS_TAG};proto=http;branch=${HG_APPS_ID}"

S = "${WORKDIR}/apps-hg/code/pumpernickel"

require recipes-qt/qt5/qt5.inc

do_install() {
    install -d ${D}${datadir}/${P}
    install -m 0755 ${B}/pumpernickel ${D}${datadir}/${P}
    cp -a ${S}/content ${D}${datadir}/${P}
}

FILES_${PN}-dbg += "${datadir}/${P}/.debug"
FILES_${PN} += "${datadir}"

RDEPENDS_${PN} = "qtdeclarative-qmlplugins qtgraphicaleffects-qmlplugins \
    gstreamer \
	"


