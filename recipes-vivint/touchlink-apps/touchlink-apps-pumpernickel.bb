# Copyright 2017 Vivint Inc.

DESCRIPTION = "User interface for Vivint sky panels"
HOMEPAGE = "http://www.vivintsky.com"
LICENSE = "CLOSED"
SECTION = "SOMETHING"
DEPENDS = "qtdeclarative qtgraphicaleffects qtmultimedia qrencode"
EXTRA_QMAKEVARS_PRE += "CONFIG+=has_qrc"
PV = "1.0.0+git${SRCPV}"
PR = "r9"

SRCREV = "${GIT_APPS_REV}"
SRCBRANCH = "${GIT_APPS_BRANCH}"

GIT_APPS_SERVER ?= "${GIT_SERVER}"
GIT_APPS_PROTOCOL ?= "ssh"

SRC_URI = "git://${GIT_APPS_SERVER}/${GIT_APPS_TAG};protocol=${GIT_APPS_PROTOCOL};branch=${SRCBRANCH} \
	   file://pumpernickel.logrotate"

S = "${WORKDIR}/git/code/pumpernickel"

require recipes-qt/qt5/qt5.inc

EXEC_DIR = "pumpernickel"

do_install() {
    install -d ${D}${datadir}/${EXEC_DIR}
    install -m 0755 ${B}/${EXEC_DIR} ${D}${datadir}/${EXEC_DIR}

    install -d "${D}${sysconfdir}/logrotate.d"
    install -m 0600 "${WORKDIR}/pumpernickel.logrotate" "${D}${sysconfdir}/logrotate.d/pumpernickel"
}

FILES_${PN}-dbg += "${datadir}/${EXEC_DIR}/.debug"
FILES_${PN} += "${datadir}"

RDEPENDS_${PN} = "qtdeclarative-qmlplugins qtgraphicaleffects-qmlplugins \
    gstreamer1.0 \
	"

pkg_postinst_${PN} () {
#!/bin/sh -e
# Post install to make sure we have the correct setup for sundance
#
if [ x"$D" = "x" ]; then
	echo "Removing old pumpernickel.log1|2|3|4|5"
	rm -f /var/log/pumpernickel.log1
	rm -f /var/log/pumpernickel.log2
	rm -f /var/log/pumpernickel.log3
	rm -f /var/log/pumpernickel.log4
	rm -f /var/log/pumpernickel.log5
else
	exit 1
fi
}

