# Copyright 2017 Vivint Inc.

DESCRIPTION = "User interface for Fcc testing"
HOMEPAGE = "http://www.vivintsky.com"
LICENSE = "CLOSED"
SECTION = "SOMETHING"
DEPENDS = "qtdeclarative touchlink-apps-pumpernickel"

PV = "1.0.0+git${SRCPV}"
PR = "r8"

SRCREV = "${GIT_APPS_REV}"
SRCBRANCH = "${GIT_APPS_BRANCH}"

GIT_APPS_SERVER ?= "${GIT_SERVER}"
GIT_APPS_PROTOCOL ?= "ssh"

SRC_URI = "git://${GIT_APPS_SERVER}/${GIT_APPS_TAG};protocol=${GIT_APPS_PROTOCOL};branch=${SRCBRANCH}"

S = "${WORKDIR}/git/code/test_ui"

require recipes-qt/qt5/qt5.inc

EXEC_DIR = "test_ui"


DEPENDS += " \ 
        python3-bcrypt-native \
        python3-cachetools \
        python3-cherrypy-native \
        python3-dateutil-native \
        python3-gnupg-native \
        python3-intelhex-native \
        python3-jinja2-native \
        python3-markupsafe-native \
        python3-mixpanel-native \
        python3-mock-native \
        python3-msgpack-native \
        python3-native \
        python3-pexpect-native \
        python3-ptyprocess-native \
        python3-psutil-native \
        python3-pyalsaaudio-native \
        python3-pycrypto-native \
        python3-pyftpdlib-native \
        python3-pyinotify-native \
        python3-pyserial-native \
        python3-pytz-native \
        python3-requests-native \
        python3-six-native \
        python3-setproctitle-native \
        python3-soco-native \
        python3-sparsedict-native \
        python3-phue-native \
        python3-paho-mqtt-native \
"

PYTHON_BASEVERSION = "3.5"
PREFERRED_VERSION_python3 = "3.5.3"
PREFERRED_VERSION_python-native = "3.5.3"

inherit python-dir pythonnative


do_configure_prepend() {
        ${S}/../../scripts/generate_all_proxies.py
}


do_install() {
    install -d ${D}${datadir}/${EXEC_DIR}
    install -m 0755 ${B}/${EXEC_DIR} ${D}${datadir}/${EXEC_DIR}
}

FILES_${PN}-dbg += "${datadir}/${EXEC_DIR}/.debug"
FILES_${PN} += "${datadir}"

RDEPENDS_${PN} = "qtdeclarative-qmlplugins \
	"
