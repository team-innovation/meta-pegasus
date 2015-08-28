DESCRIPTION = "Slimline qt5 image."
LICENSE = "MIT"
PR = "r5"
IMAGE_FEATURES += "package-management" 

IMAGE_INSTALL_append = "\
	${CORE_IMAGE_BASE_INSTALL} \
	packagegroup-slimline-common \
	packagegroup-touchlink-apps \ 
	packagegroup-slimline-video \
	alsa-utils-aplay \
	pulseaudio-meta \
	python3-pyqt5 \
	audio-sh \
"

inherit core-image
