DESCRIPTION = "Slimline qt5 image."
LICENSE = "MIT"
PR = "r7"
IMAGE_FEATURES += "package-management" 

IMAGE_INSTALL_append = "\
	${CORE_IMAGE_BASE_INSTALL} \
	packagegroup-sawmill-common \
	packagegroup-touchlink-apps-slimline \
	packagegroup-sawmill-video \
	alsa-utils-aplay \
	pulseaudio-meta \
	python3-pyqt5 \
	audio-sh \
	mtd-utils \
"

inherit core-image

IMAGE_FSTYPES_append = " emmc"
