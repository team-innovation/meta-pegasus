# Copyright (C) 2020 Vivint, Inc.


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
	qtquickcontrols2 \
	qtquickcontrols2-qmlplugins \
	qtscript 	\
"

PULSE_DEPENDS = "python3-pulseaudio \
"

MISC_DEPENDS = "\             
        ppp \ 
        socat \
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
	${PULSE_DEPENDS} \
	${MISC_DEPENDS} \
	${PYTHON_EXTRA_DEPENDS} \
	${QT5_DEPENDS} \
	${QT5_PKGS} \
"
