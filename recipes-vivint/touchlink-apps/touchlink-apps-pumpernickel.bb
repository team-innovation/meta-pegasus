# Copyright 2017 Vivint Inc.

DESCRIPTION = "User interface for Vivint sky panels"
HOMEPAGE = "http://www.vivintsky.com"
LICENSE = "CLOSED"
SECTION = "SOMETHING"
DEPENDS = "qtdeclarative qtgraphicaleffects qtmultimedia"

PV = "1.0.0+git${SRCPV}"
PR = "r7"

SRCREV = "${GIT_APPS_REV}"
SRCBRANCH = "${GIT_APPS_BRANCH}"

GIT_APPS_SERVER ?= "${GIT_SERVER}"
GIT_APPS_PROTOCOL ?= "ssh"

SRC_URI = "git://${GIT_APPS_SERVER}/${GIT_APPS_TAG};protocol=${GIT_APPS_PROTOCOL};branch=${SRCBRANCH}"

S = "${WORKDIR}/git/code/pumpernickel"

require recipes-qt/qt5/qt5.inc

EXEC_DIR = "pumpernickel"

do_install() {
    install -d ${D}${datadir}/${EXEC_DIR}
    install -m 0755 ${B}/${EXEC_DIR} ${D}${datadir}/${EXEC_DIR}
}

FILES_${PN}-dbg += "${datadir}/${EXEC_DIR}/.debug"
FILES_${PN} += "${datadir}"

RDEPENDS_${PN} = "qtdeclarative-qmlplugins qtgraphicaleffects-qmlplugins \
    gstreamer1.0 \
	"


