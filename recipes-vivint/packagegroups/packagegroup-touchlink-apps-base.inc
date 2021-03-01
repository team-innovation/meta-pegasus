# Copyright (C) 2015 Vivint, Inc.

inherit packagegroup

VIVINT_APPS_BASE = "\
	touchlink-apps-favicon \
	touchlink-apps-framework \
	touchlink-apps-global-conf \
	touchlink-apps-iod \
	touchlink-apps-netd \
	touchlink-apps-procmand \
	touchlink-apps-audmgrd \
	touchlink-apps-sound-wav-chimes \
	touchlink-apps-ssdpd \
	touchlink-apps-sundance-proxies \
	touchlink-apps-updated \
	touchlink-apps-utils \
	touchlink-apps-modemd-proxies \
	touchlink-simlock \
	touchlink-ssl-profile \
	voiceaudio \
"

PULSE_DEPENDS = "python3-pulseaudio \
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

RDEPENDS_${PN} = " \
	${MISC_DEPENDS} \
	${PULSE_DEPENDS} \
	${PYTHON_EXTRA_DEPENDS} \
	${VIVINT_APPS_BASE} \
"
