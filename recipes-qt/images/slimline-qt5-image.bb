DESCRIPTION = "Slimline qt5 image."
LICENSE = "MIT"
PR = "r3"
IMAGE_FEATURES += "package-management" 

IMAGE_INSTALL += "\
	${CORE_IMAGE_BASE_INSTALL} \
	packagegroup-slimline-mfg \
	packagegroup-touchlink-apps \ 
	packagegroup-slimline-video \
	kernel-image \
	kernel-devicetree \
	u-boot-fw-utils \
	u-boot-slimline \
	alsa-utils-aplay \
	python3-distribute \
	python3-misc \
	python3-subprocess \
	python3-pyqt5 \
	strace \
	psoc-fw \
	slimline-modules \
	wps-sh \
	fcc-gunk \
	sqlite3 \
	ctailcat \
<<<<<<< 435d5c835143f320f70bde52f5b275da87535ae9
	ntp \
	touchlink-ntpsync \
=======
	audio-sh \
	pulseaudio-meta \
>>>>>>> 87d1ee840a706c3883f3b07faccbfc5704b92d8f
"

inherit core-image
