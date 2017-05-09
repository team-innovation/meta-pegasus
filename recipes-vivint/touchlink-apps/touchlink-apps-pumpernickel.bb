# Copyright 2017 Vivint Inc.

DESCRIPTION = "User interface for Vivint sky panels"
HOMEPAGE = "http://www.vivintsky.com"
LICENSE = "CLOSED"
SECTION = "SOMETHING"
DEPENDS = "qtdeclarative qtgraphicaleffects"
PR = "r1"

HG_APPS_REV = "pumpernickel_diy"
SRCREV = "42631bbb6888"


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

RDEPENDS_${PN} = "qtdeclarative-qmlplugins qtgraphicaleffects-qmlplugins \
    gstreamer \
	gst-plugin-playbin \
        gst-plugin-rtsp \
        gst-plugin-level \
	gst-plugin-autodetect \
	gst-plugin-mulaw \
	gst-plugin-alaw \
	gst-plugin-isomp4 \
	gst-rtsp-server \
	"


