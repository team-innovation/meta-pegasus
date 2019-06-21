# Copyright 2017 Vivint Inc.

DESCRIPTION = "User interface for Vivint sky panels"
HOMEPAGE = "http://www.vivintsky.com"
LICENSE = "CLOSED"
SECTION = "SOMETHING"
DEPENDS = "qtdeclarative qtgraphicaleffects qtmultimedia qrencode qtdeclarative-native"
EXTRA_QMAKEVARS_PRE += "CONFIG+=has_qrc"
PV = "1.0.0+git${SRCPV}"
PR = "r14"

DEPENDS += " \
	touchlink-apps \
	python3-bcrypt-native \
	python3-cachetools \
	python3-cherrypy-native \
	python3-dateutil-native \
	python3-gnupg-native \
	python3-grpcio-native \
	python3-grpcio-tools-native \
	python3-googleapis-common-protos-native \
	python3-intelhex-native \
	python3-jinja2-native \
	python3-idna-native \
	python3-markupsafe-native \
	python3-mixpanel-native \
	python3-mock-native \
	python3-msgpack-native \
	python3-native \
	python3-pexpect-native \
	python3-protobuf-native \
	python3-ptyprocess-native \
	python3-psutil-native \
	python3-pyalsaaudio-native \
	python3-pycrypto-native \
	python3-pyftpdlib-native \
	python3-pyinotify-native \
	python3-pyserial-native \
	python3-pyopenssl-native \
	python3-pytz-native \
	python3-requests-native \
	python3-six-native \
	python3-setproctitle-native \
	python3-soco-native \
	python3-sparsedict-native \
	python3-phue-native \
	python3-paho-mqtt-native \
"

SRCREV = "${GIT_APPS_REV}"
SRCBRANCH = "${GIT_APPS_BRANCH}"

GIT_APPS_SERVER ?= "${GIT_SERVER}"
GIT_APPS_PROTOCOL ?= "ssh"

SRC_URI = "git://${GIT_APPS_SERVER}/${GIT_APPS_TAG};protocol=${GIT_APPS_PROTOCOL};branch=${SRCBRANCH} \
	   file://pumpernickel.logrotate"

S = "${WORKDIR}/git/code/pumpernickel"

require recipes-qt/qt5/qt5.inc

PYTHON_BASEVERSION = "3.5"
PREFERRED_VERSION_python3 = "3.5.3"
PREFERRED_VERSION_python-native = "3.5.3"

inherit python3-dir python3native

EXEC_DIR = "pumpernickel"

do_configure_prepend() {
	export PYTHONPATH=${S}/../sundance/services/devices/generated/grpc:$PYTHONPATH
	${S}/../../scripts/generate_all_proxies.py
}

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

pkg_postinst_ontarget_${PN} () {
#!/bin/sh -e
# Post install to make sure we have the correct setup for sundance

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

