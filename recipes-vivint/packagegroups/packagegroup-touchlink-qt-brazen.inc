# Copyright (C) 2020 Vivint, Inc.


VIVINT_APPS_COMMON = "\
	touchlink-apps-qml-framework \
	touchlink-apps-qml-framework-images-controls \
	touchlink-apps-qml-framework-images-controls-shared \
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
	qtquickcontrols-qmlplugins \
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
