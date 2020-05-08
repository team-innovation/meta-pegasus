# Copyright (C) 2015 Vivint, Inc.

require packagegroup-touchlink-apps-base.inc

VIVINT_APPS_COMMON = "\
	touchlink-apps-launcherd \
	touchlink-apps-qml-framework \
	touchlink-apps-qml-framework-images-controls \
	touchlink-apps-qml-framework-images-controls-shared \
	touchlink-apps-pumpernickel \
	touchlink-apps-initpumpernickel \
	touchlink-apps-roubaix-services \
	touchlink-apps-usage-analytics \
"

QT5_DEPENDS = "\
	libegl-mx6 \
    	libgal-mx6 \
    	libvivante-mx6 \
	libgles-mx6 \
	libgles2-mx6 \
    	libopenvg-mx6 \
    	imx-gpu-viv-g2d \
    	imx-gpu-viv-kernel \
	python3-pyopengl \
	touchlink-qt5-profile \
"

QT5_PKGS = "\
	libqt5declarative5 \
	libqt5xmlpatterns5 \
	mtdev \
	qtbase	\
	qtbase-fonts-pfa  \
	qtbase-fonts-pfb  \
	qtbase-fonts-qpf  \
	qtbase-fonts-ttf-dejavu   \
	qtbase-fonts-ttf-vera     \
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
