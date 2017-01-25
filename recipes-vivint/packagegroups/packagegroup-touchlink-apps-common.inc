# Copyright (C) 2015 Vivint, Inc.

inherit packagegroup

VIVINT_APPS_COMMON = "\
	touchlink-apps-favicon \
	touchlink-apps-framework \
	touchlink-apps-global-conf \
	touchlink-apps-iod \
	touchlink-apps-launcherd \
	touchlink-apps-netd \
	touchlink-apps-nfcd \
	touchlink-apps-procmand \
	touchlink-apps-qml-framework \
	touchlink-apps-qml-framework-images-controls \
	touchlink-apps-qml-framework-images-controls-shared \
	touchlink-apps-roubaix \
	touchlink-apps-roubaix-audio-platform \
	touchlink-apps-roubaix-audmgr-definitions \
	touchlink-apps-roubaix-controls \
	touchlink-apps-roubaix-defs \
	touchlink-apps-roubaix-demo-assets \
	touchlink-apps-roubaix-fonts \
	touchlink-apps-roubaix-framework \
	touchlink-apps-roubaix-gunk \
	touchlink-apps-roubaix-html \
	touchlink-apps-roubaix-images-coin \
	touchlink-apps-roubaix-images-shared \
	touchlink-apps-roubaix-images-toolbox \
	touchlink-apps-roubaix-images-video \
	touchlink-apps-roubaix-images-wallpapers \
	touchlink-apps-roubaix-mixin \
	touchlink-apps-roubaix-models \
	touchlink-apps-roubaix-popups \
	touchlink-apps-roubaix-scripts \
	touchlink-apps-roubaix-services \
	touchlink-apps-roubaix-style \
	touchlink-apps-roubaix-views \
	touchlink-apps-sound-roubaix-back \
	touchlink-apps-sound-roubaix-chimes \
	touchlink-apps-sound-roubaix-click \
	touchlink-apps-sound-roubaix-dialog \
	touchlink-apps-sound-roubaix-locks \
	touchlink-apps-sound-roubaix-numpad \
	touchlink-apps-sound-roubaix-operations \
	touchlink-apps-sound-roubaix-switch \
	touchlink-apps-sound-wav-abs \
	touchlink-apps-sound-wav-ad \
	touchlink-apps-sound-wav-beeps \
	touchlink-apps-sound-wav-dtmf \
	touchlink-apps-sound-wav-pauses \
	touchlink-apps-sound-wav-vocab \
	touchlink-apps-ssdpd \
	touchlink-apps-sundance-proxies \
	touchlink-apps-zwaved-proxies \
	touchlink-apps-updated \
	touchlink-apps-usage-analytics \
	touchlink-apps-utils \
	touchlink-simlock \
	voiceaudio \
	"

QT5_DEPENDS = "\
	libegl-mx6 \
	libgles2-mx6 \
	python3-pyopengl \
	touchlink-qt5-profile \
	"

QT5_PKGS = "\
	mtdev \
	tslib tslib-calibrate tslib-tests \
	libqt5declarative5 \
	libqt5xmlpatterns5 \
	qtbase	\
	qtbase-plugins	\
	qtscript 	\
	qtdeclarative 	\
	qtdeclarative-tools \
	qtdeclarative-qmlplugins \
	qtgraphicaleffects-qmlplugins \
	qtmultimedia-plugins \
	qtmultimedia-qmlplugins \
	qtbase-fonts-pfa  \
	qtbase-fonts-pfb  \
	qtbase-fonts-qpf  \
	qtbase-fonts-ttf-dejavu   \
	qtbase-fonts-ttf-vera     \
	qtgstreamer \
    qtquickcontrols-qmlplugins \
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
	"

PYTHON_EXTRA_DEPENDS = "\
	python3-mixpanel \
	python3-multiprocessing \
	python3-mmap \
	python3-requests \
	python3-six \
	python3-soco \
	python3-jsonschema \
	python3-brisa \
	python3-sparsedict \
	"

RDEPENDS_${PN} = " \
	${VIVINT_APPS_COMMON} \
	${QT5_DEPENDS} \
	${QT5_PKGS} \
	${PULSE_DEPENDS} \
	${TOUCHSCREEN_DEPENDS} \
	${DISPLAY_DEPENDS} \
	${MISC_DEPENDS} \
	${PYTHON_EXTRA_DEPENDS} \
"
