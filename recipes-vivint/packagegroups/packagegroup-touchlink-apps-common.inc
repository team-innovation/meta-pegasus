# Copyright (C) 2015 Vivint, Inc.

require packagegroup-touchlink-apps-base.inc

VIVINT_APPS_COMMON = "\
	touchlink-apps-launcherd \
	touchlink-apps-qml-framework \
	touchlink-apps-qml-framework-images-controls \
	touchlink-apps-qml-framework-images-controls-shared \
	touchlink-apps-pumpernickel \
	touchlink-apps-httpd \
	touchlink-apps-initpumpernickel \
	touchlink-apps-roubaix-services \
	touchlink-apps-usage-analytics \
"

QT5_DEPENDS = "\
	libegl-imx \
    	libgal-imx \
	libgles-imx \
	libgles2-imx \
    	libopenvg-imx \
    	imx-gpu-g2d \
	python3-pyopengl \
	touchlink-ssl-profile \
"

QT5_PKGS = "\
	mtdev \
	qtbase	\
	qtbase-plugins	\
	qtdeclarative 	\
	qtdeclarative-qmlplugins \
	qtdeclarative-tools \
	qtgraphicaleffects-qmlplugins \
	qtmultimedia-plugins \
	qtmultimedia-qmlplugins \
	qtquickcontrols2 \
	qtquickcontrols2-qmlplugins \
	qtscript 	\
	tslib tslib-calibrate tslib-tests \
"

PULSE_DEPENDS = "python3-pulseaudio \
		"
TOUCHSCREEN_DEPENDS = "libinput \
	evtest \
	touchlink-maxtouch-config \
"

DISPLAY_DEPENDS = "\
	imagemagick \
"

MISC_DEPENDS = "\
	ppp \
	socat \
	live555-openrtsp \
	nfc-util \
"

PYTHON_EXTRA_DEPENDS = "\
	python3-brisa \
	python3-certifi \
	python3-urllib3 \
	python3-sentry-sdk \
	python3-jsonschema \
	python3-mmap \
	python3-multiprocessing \
	python3-requests \
	python3-requests-toolbelt \
	python3-six \
	python3-soco \
	python3-sparsedict \
	python3-phue \
	python3-transitions \
"

RDEPENDS_${PN} += " \
	${DISPLAY_DEPENDS} \
	${MISC_DEPENDS} \
	${PULSE_DEPENDS} \
	${PYTHON_EXTRA_DEPENDS} \
	${QT5_DEPENDS} \
	${QT5_PKGS} \
	${TOUCHSCREEN_DEPENDS} \
	${VIVINT_APPS_COMMON} \
"
