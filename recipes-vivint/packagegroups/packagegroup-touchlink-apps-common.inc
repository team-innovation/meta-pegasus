# Copyright (C) 2015 Vivint, Inc.

inherit packagegroup

VIVINT_APPS_COMMON = "\
	touchlink-apps-favicon \
	touchlink-apps-framework \
	touchlink-apps-global-conf \
	touchlink-apps-iod \
	touchlink-apps-launcherd \
	touchlink-apps-netd \
	touchlink-apps-procmand \
	touchlink-apps-qml-framework \
	touchlink-apps-qml-framework-images-controls \
	touchlink-apps-qml-framework-images-controls-shared \
	touchlink-apps-audmgrd \
	touchlink-apps-pumpernickel \
	touchlink-apps-initpumpernickel \
	touchlink-apps-roubaix-services \
	touchlink-apps-sound-wav-chimes \
	touchlink-apps-ssdpd \
	touchlink-apps-sundance-proxies \
	touchlink-apps-smarthomed-proxies \
	touchlink-apps-listenerd-proxies \
	touchlink-apps-updated \
	touchlink-apps-usage-analytics \
	touchlink-apps-utils \
	touchlink-apps-zwaved-proxies \
	touchlink-apps-modemd-proxies \
	touchlink-simlock \
	voiceaudio \
"

QT5_DEPENDS = "\
	libegl-mx6 \
    	libgal-mx6 \
    	libvivante-mx6 \
	libgles-mx6 \
	libgles2-mx6 \
    	libopenvg-mx6 \
    	imx-gpu-viv-g2d \
	python3-pyopengl \
	touchlink-qt5-profile \
	touchlink-ssl-profile \
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

PULSE_DEPENDS = "python3-pulseaudio \
		"
TOUCHSCREEN_DEPENDS = "libinput \
	evtest \
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
	python3-jsonschema \
	python3-mixpanel \
	python3-mmap \
	python3-multiprocessing \
	python3-requests \
	python3-six \
	python3-soco \
	python3-sparsedict \
	python3-phue \
	python3-transitions \
"

RDEPENDS_${PN} = " \
	${DISPLAY_DEPENDS} \
	${MISC_DEPENDS} \
	${PULSE_DEPENDS} \
	${PYTHON_EXTRA_DEPENDS} \
	${QT5_DEPENDS} \
	${QT5_PKGS} \
	${TOUCHSCREEN_DEPENDS} \
	${VIVINT_APPS_COMMON} \
"
