DESCRIPTION = "Slimline qt5 image."
LICENSE = "MIT"
PR = "r8"
IMAGE_FEATURES += "package-management" 

IMAGE_INSTALL_append = "\
	${CORE_IMAGE_BASE_INSTALL} \
	packagegroup-sawmill-common \
	packagegroup-touchlink-apps-slimline \
	packagegroup-sawmill-slimline \
	packagegroup-sawmill-video \
	alsa-utils-aplay \
	pulseaudio-meta-slimline \
	python3-pyqt5 \
	audio-sh \
	mtd-utils \
"

inherit core-image

license_create_manifest_append() {
# We don't want some of the files installed by the license helper scripts
    if [ -e ${IMAGE_ROOTFS}/usr/share/common-licenses/ ]; then
        rm -f ${IMAGE_ROOTFS}/usr/share/common-licenses/license.manifest
        rm -f ${IMAGE_ROOTFS}/usr/share/common-licenses/generic_*
        for d in $(find ${IMAGE_ROOTFS}/usr/share/common-licenses -type d); do
           rm -f "$d"/generic_* 
           rmdir --ignore-fail-on-non-empty "$d"
        done

    fi

    # Make a link so roubaix is happy
    if [ ! -e ${IMAGE_ROOTFS}/usr/share/licenses ]; then
        ln -s common-licenses ${IMAGE_ROOTFS}/usr/share/licenses
    fi
}


IMAGE_FSTYPES_append = " emmc"
