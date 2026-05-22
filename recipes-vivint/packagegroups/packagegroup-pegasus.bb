DESCRIPTION = "Package group used by Vivint for Pegasus thermostat" 
SUMMARY = "Vinint package group - pegasus"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = " \
	ca-certificates \
	i2c-tools \
	kernel-image \
	kernel-devicetree \
	libgpiod \
	libubootenv-bin \
        lrzsz \
        lsb-release \
	mmc-utils \
        mosquitto \
        mosquitto-clients \
        openssh \
	paho-mqtt-cpp \
	paho-mqtt-c \
	protobuf \
	protobuf-c \
	pulseaudio-server \
	pulseaudio-misc \
        qtdeclarative \
        qtdeclarative-qmlplugins \
        qtmultimedia \
        qtmultimedia-qmlplugins \
        avahi-utils \
	bridge-utils \
	strace \
	zeroconf \
        weston \
        weston-examples \
        udev-rules-vivint \
"

# NXP i.MX SPECIFIC PACKAGES
RDEPENDS:${PN}:append:imx8mn-pegasus = " \
        imx-boot \
        u-boot-imx \
        u-boot-script-vivint \
"
