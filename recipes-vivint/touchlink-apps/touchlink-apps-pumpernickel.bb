# Copyright 2017 Vivint Inc.

DESCRIPTION = "User interface for Vivint sky panels"
HOMEPAGE = "http://www.vivintsky.com"
LICENSE = "CLOSED"
SECTION = "SOMETHING"
DEPENDS = "qtdeclarative qtgraphicaleffects"

PV = "1.0.0+git${SRCPV}"

SRCREV = "${GIT_APPS_REV}"
SRCBRANCH = "${GIT_APPS_BRANCH}"

GIT_SERVER_APPS ?= "${GIT_SERVER}"

SRC_URI = "git://${GIT_SERVER_APPS}/${GIT_APPS_TAG};protocol=ssh;branch=${SRCBRANCH}"

S = "${WORKDIR}/git/code/pumpernickel"

require recipes-qt/qt5/qt5.inc

EXEC_DIR = "pumpernickel"

do_install() {
    install -d ${D}${datadir}/${EXEC_DIR}
    install -m 0755 ${B}/${EXEC_DIR} ${D}${datadir}/${EXEC_DIR}
    cp -a ${S}/content ${D}${datadir}/${EXEC_DIR}
}

FILES_${PN}-dbg += "${datadir}/${EXEC_DIR}/.debug"
FILES_${PN} += "${datadir}"

RDEPENDS_${PN} = "qtdeclarative-qmlplugins qtgraphicaleffects-qmlplugins \
    gstreamer \
	"


