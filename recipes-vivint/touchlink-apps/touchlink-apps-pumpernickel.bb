# Copyright 2017 Vivint Inc.

DESCRIPTION = "User interface for Vivint sky panels"
HOMEPAGE = "http://www.vivintsky.com"
LICENSE = "CLOSED"
SECTION = "SOMETHING"
DEPENDS = "qtbase qtdeclarative qtgraphicaleffects qtmultimedia qrencode qtbase-native qtdeclarative-native"
EXTRA_QMAKEVARS_PRE += "CONFIG+=has_qrc"
PV = "1.0.0+git${SRCPV}"
PR = "r23"

DEPENDS += " \
        gstreamer1.0-plugins-bad \
        gstreamer1.0-plugins-base \
        gstreamer1.0-plugins-good \
        gstreamer1.0 \
        python3-setuptools-native \
        python3-aiodns-native \
        python3-aioconsole-native \
        python3-aiohttp-native \
        python3-async-generator-native \
        python3-async-timeout-native \
        python3-atomicwrites-native \
        python3-attrs-native \
        python3-bcrypt-native \
        python3-bson-native \
        python3-cachetools \
        python3-cachetools-native \
        python3-cchardet-native \
        python3-cherrypy-native \
        python3-cstruct-native \
        python3-coverage-native \
        python3-certifi-native \
        python3-cython-native \
        python3-dateutil-native \
        python3-gnupg-native \
        python3-grpcio-native \
        python3-pycares-native \
        python3-multidict-native \
        python3-terminaltables-native \
        python3-idna-native \
        python3-idna-ssl-native \
        python3-grpcio-tools-native \
        python3-googleapis-common-protos-native \
        python3-intelhex-native \
        python3-jinja2-native \
        python3-jsonschema-native \
        python3-markupsafe-native \
        python3-mixpanel-native \
        python3-mock-native \
        python3-more-itertools-native \
        python3-msgpack-native \
        python3-native \
        python3-pathlib2-native \
        python3-paho-mqtt-native \
        python3-pexpect-native \
        python3-phue-native \
        python3-pluggy-native \
        python3-protobuf-native \
	python3-pyjwt-native \
        python3-ptyprocess-native \
        python3-psutil-native \
        python3-pyalsaaudio-native \
        python3-pycrypto-native \
        python3-pyftpdlib-native \
        python3-pytest-native \
        python3-pytest-asyncio-native \
        python3-pytest-cov-native \
        python3-pyinotify-native \
        python3-pyserial-native \
        python3-pysodium-native \
        python3-py-native \
        python3-pytz-native \
        python3-requests-native \
        python3-requests-toolbelt-native \
        python3-easydict-native \
        python3-six-native \
        python3-setproctitle-native \
        python3-soco-native \
        python3-sparsedict-native \
        python3-sentry-sdk-native \
        python3-toolz-native \
        python3-transitions-native \
        python3-typing-extensions-native \
        python3-uvloop-native \
        python3-wheel-native \
        python3-urllib3-native \
        python3-xmltodict-native \
        python3-yarl-native \
        rsync-native \
        libsodium-native \
        python3-pyopenssl-native \
        python3-cryptography-native \
        python3-cffi-native \
        python3-asn1crypto-native \
        python3-pycparser-native \
        python3-chardet-native \
        python3-certifi-native \
        breakpad \
        variant-lite \
        taocpp-json \
        zipgateway \
        zware \
        iotivity \
        zwave-nvm-converter \
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

EXTRA_QMAKEVARS_PRE += "INCLUDEPATH+=${STAGING_INCDIR} LIBS+=-L${STAGING_LIBDIR}"

inherit python3-dir python3native

EXEC_DIR = "pumpernickel"

do_configure_prepend() {
	export PYTHONPATH=${S}/../sundance/services/devices/generated/grpc:$PYTHONPATH
	${S}/../../scripts/generate_all_proxies.py

    if [ ! ${SKIP_GENERATE_AUDIO} ]; then
    	python3 ${S}/../../scripts/tts_wav_generator.py --json_file ${S}/tts_inputs.json --path ${S}/wav/
    fi
}

do_install() {
    install -d ${D}${datadir}/${EXEC_DIR}
    install -m 0755 ${B}/${EXEC_DIR} ${D}${datadir}/${EXEC_DIR}

    install -d ${D}/opt/2gig/soundfiles/wav
    install -d ${D}/opt/2gig/soundfiles/wav/clicks
    cp -dR ${S}/wav/* ${D}/opt/2gig/soundfiles/wav
    cp -dR ${S}/content/sounds/* ${D}/opt/2gig/soundfiles/wav/clicks

    install -d ${D}/opt/2gig/images
    install -m 0600 ${S}/content/images/wallpapers/splash_1024x600.png ${D}/opt/2gig/images

    install -d "${D}${sysconfdir}/logrotate.d"
    install -m 0600 "${WORKDIR}/pumpernickel.logrotate" "${D}${sysconfdir}/logrotate.d/pumpernickel"
}

FILES_${PN}-dbg += "${datadir}/${EXEC_DIR}/.debug"
FILES_${PN} += "${datadir} /opt/2gig/soundfiles/* /opt/2gig/images/*"

RDEPENDS_${PN} = "qtdeclarative-qmlplugins qtgraphicaleffects-qmlplugins \
    qtsvg-plugins \
    gstreamer1.0 \
    libunwind \
    weston \
    weston-init \
    qtwayland \
    qtwayland-plugins \
    qtwayland-qmlplugins \
	"

pkg_postinst_ontarget_${PN}-pumpernickel () {

	echo "Removing old pumpernickel.log1|2|3|4|5"
	rm -f /var/log/pumpernickel.log1
	rm -f /var/log/pumpernickel.log2
	rm -f /var/log/pumpernickel.log3
	rm -f /var/log/pumpernickel.log4
	rm -f /var/log/pumpernickel.log5

}

