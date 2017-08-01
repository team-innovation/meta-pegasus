DESCRIPTION = "Sly qt5 image."
LICENSE = "MIT"
PR = "r10"
IMAGE_FEATURES += "package-management" 

IMAGE_INSTALL_append = "\
	${CORE_IMAGE_BASE_INSTALL} \
	packagegroup-sawmill-common \
	packagegroup-touchlink-apps-sly \
	packagegroup-sawmill-sly \
	packagegroup-sawmill-video \
	alsa-utils-aplay \
	pulseaudio-meta-sly \
	python3-pyqt5 \
	audio-sh \
	mtd-utils \
	packagegroup-camera-firmware \
	packagegroup-ppp-config-modem \
	packagegroup-zwave-config \
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

do_image_complete() {
	echo "*******"
	echo "APPS"
	echo "SERVER: ${GIT_APPS_SERVER}"
	echo "BRANCH: ${GIT_APPS_BRANCH}"
	echo "PROTOCOL: ${GIT_APPS_PROTOCOL}"
	echo "*******"
	echo "KERNEL"
	echo "SERVER: ${GIT_KERNEL_SERVER}"
	echo "BRANCH: ${GIT_KERNEL_BRANCH}"
	echo "PROTOCOL: ${GIT_KERNEL_PROTOCOL}"
	echo "*******"
	echo "AUDIO"
	echo "SERVER: ${GIT_AUDIO_SERVER}"
	echo "BRANCH: ${GIT_AUDIO_BRANCH}"
	echo "PROTOCOL: ${GIT_AUDIO_PROTOCOL}"
	echo "*******"
	echo "UBOOT"
	echo "SERVER: ${GIT_UBOOT_SERVER}"
	echo "BRANCH: ${GIT_UBOOT_BRANCH}"
	echo "PROTOCOL: ${GIT_UBOOT_PROTOCOL}"
	echo "*******"
}
