# Copyright (C) 2015 Vivint, Inc.

DESCRIPTION = "touchlink apps packages, framework, roubaix, etc"
LICENSE = "MIT"

inherit packagegroup

VIVINT_APPS = "touchlink-apps \
	touchlink-apps-345d \
	touchlink-apps-audmgrd \
	touchlink-apps-cloudd \
	touchlink-apps-dbapd \
	touchlink-apps-favicon \
	touchlink-apps-framework \
	touchlink-apps-global-conf \
	touchlink-apps-httpd \
	touchlink-apps-iod \
	touchlink-apps-modemd \
	touchlink-apps-netd \
	touchlink-apps-procmand \
	touchlink-apps-pyftpd \
	touchlink-apps-qml-framework \
	touchlink-apps-qml-framework-controls \
	touchlink-apps-qml-framework-images-controls \
	touchlink-apps-qml-framework-images-controls-shared \
	touchlink-apps-qml-framework-popups \
	touchlink-apps-qml-framework-scripts \
	touchlink-apps-roubaix \
	touchlink-apps-roubaix-controls \
	touchlink-apps-roubaix-fonts \
	touchlink-apps-roubaix-framework \
	touchlink-apps-roubaix-gunk \
	touchlink-apps-roubaix-html \
	touchlink-apps-roubaix-images-controls \
	touchlink-apps-roubaix-images-shared \
	touchlink-apps-roubaix-images-toolbox \
	touchlink-apps-roubaix-images-video \
	touchlink-apps-roubaix-images-wallpapers \
	touchlink-apps-roubaix-legal \
	touchlink-apps-roubaix-models \
	touchlink-apps-roubaix-popups \
	touchlink-apps-roubaix-scripts \
	touchlink-apps-roubaix-services \
	touchlink-apps-roubaix-style \
	touchlink-apps-roubaix-themes-alabaster \
	touchlink-apps-roubaix-themes-aqua \
	touchlink-apps-roubaix-themes-forest \
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
	touchlink-apps-sundance \
	touchlink-apps-updated \
	touchlink-apps-utils \
	touchlink-apps-webd \
	touchlink-apps-zwaved \
	"

QT5_DEPENDS = "libegl-mx6 \
	libgles2-mx6 \
	touchlink-qt5-profile \
	"

QT5_PKGS = "tslib tslib-calibrate tslib-tests \
	libqt5declarative5 \
	libqt5xmlpatterns5 \
	qtbase	\
	qtbase-plugins	\
	qtscript 	\
	qtdeclarative 	\
	qtdeclarative-qmlplugins \
	qtgraphicaleffects-qmlplugins \
        qtmultimedia-plugins \
        qtmultimedia-qmlplugins \
	qtbase-fonts-pfa  \
	qtbase-fonts-pfb  \
	qtbase-fonts-qpf  \
	qtbase-fonts-ttf-dejavu   \
	qtbase-fonts-ttf-vera     \
	"

PULSE_DEPENDS = "python3-pulseaudio \
		"

RDEPENDS_${PN} = " \
	${VIVINT_APPS} \
	${QT5_DEPENDS} \
	${QT5_PKGS} \
	${PULSE_DEPENDS} \
	python3-ctypes \
	python3-importlib \
	python3-jinja2 \
	python3-json \
	python3-math \
	python3-msgpack \
	python3-netserver \
	python3-re \
	python3-setproctitle \
	python3-shell \
	python3-sip \
	python3-sqlite3 \
	python3-textutils \
	python3-terminal \
	python3-resource \
	python3-pexpect-u \
"
