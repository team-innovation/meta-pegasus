# Copyright (C) 2020 Vivint, Inc.


VIVINT_APPS_COMMON = "\
	touchlink-apps-qml-framework \
	touchlink-apps-qml-framework-images-controls \
	touchlink-apps-qml-framework-images-controls-shared \
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
	qtmultimedia-plugins \
	qtmultimedia-qmlplugins \
	qtgraphicaleffects-qmlplugins \
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
