DESCRIPTION = "Package group used by Vivint for Pegasus thermostat" 
SUMMARY = "Vinint package group - pegasus"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = " \
	ca-certificates \
	i2c-tools \
	imx-boot \
	kernel-image \
	kernel-devicetree \
	libgpiod \
	libubootenv-bin \
	mmc-utils \
    mosquitto \
    mosquitto-clients \
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
	u-boot-imx \
	zeroconf \
"
