DESCRIPTION = "Brazen image."
LICENSE = "MIT"
PR = "r1"
IMAGE_FEATURES += "package-management" 

DEPENDS += "zip-native"
IMAGE_INSTALL_append = "\
	${CORE_IMAGE_BASE_INSTALL} \
	packagegroup-sawmill-common \
	packagegroup-touchlink-apps-brazen \
	packagegroup-sawmill-brazen \
	packagegroup-sawmill-video \
	alsa-utils-aplay \
	pulseaudio-meta-sly \
	audio-sh \
	mtd-utils \
	packagegroup-camera-firmware \
	packagegroup-ppp-config-modem \
	packagegroup-zwave-config \
"

inherit core-image

license_create_manifest_append() {
    rootfs_licensce_path = os.path.join(d.getVar('IMAGE_ROOTFS'),'/usr/share/common-licenses/license.manifest')

    # We don't want some of the files installed by the license helper scripts
    if os.path.exists(os.path.join(d.getVar('IMAGE_ROOTFS'),'/usr/share/common-licenses/license.manifest')):
        os.path.remove(os.path.join(d.getVar('IMAGE_ROOTFS'),'/usr/share/common-licenses/license.manifest'))
        os.path.remove(os.path.join(d.getVar('IMAGE_ROOTFS') ,'/usr/share/common-licenses/generic_*'))
        for top, dirs, files in os.walk(os.path.join(d.getVar('IMAGE_ROOTFS'), '/usr/share/common-licenses'), topdown=False):
            if "generic_" in dirs:
                os.path.remove(top, dir)

    # Make a link so roubaix is happy
    if os.path.exists(os.path.join(d.getVar('IMAGE_ROOTFS'),'/usr/share/common-licenses/license.manifest')):
        os.symlink('common-licenses', os.path.join(d.getVar('IMAGE_ROOTFS'),'/usr/share/licenses'))
}

IMAGE_FSTYPES_append = " emmc"
