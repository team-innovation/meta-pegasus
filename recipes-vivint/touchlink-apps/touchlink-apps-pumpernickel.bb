# Copyright 2017 Vivint Inc.

DESCRIPTION = "User interface for Vivint sky panels"
HOMEPAGE = "http://www.vivintsky.com"
LICENSE = "CLOSED"
SECTION = "SOMETHING"
DEPENDS = "qtdeclarative qtgraphicaleffects"
PR = "r5"

SRCREV = "${GIT_APPS_REV}"
SRCBRANCH = "${GIT_APPS_BRANCH}"

SRC_URI = "git://git@${GIT_SERVER}/${GIT_APPS_TAG}.git;proto=ssh;branch=${SRCBRANCH}"

S = "${WORKDIR}/git/code/pumpernickel"

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


