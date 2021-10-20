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
	qtquickcontrols2 \
	qtquickcontrols2-qmlplugins \
	qtscript 	\
"

PULSE_DEPENDS = "python3-pulseaudio \
		"

RDEPENDS_${PN} += " \
	${MISC_DEPENDS} \
	${PULSE_DEPENDS} \
	${PYTHON_EXTRA_DEPENDS} \
	${QT5_DEPENDS} \
	${QT5_PKGS} \
	${VIVINT_APPS_COMMON} \
"
