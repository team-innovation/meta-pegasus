UMMARY = "Paho MQTT - C++ libraries for the MQTT and MQTT-SN protocols"
DESCRIPTION = "Client implementation of open and standard messaging protocols for Machine-to-Machine (M2M) and Internet of Things (IoT)."
HOMEPAGE = "http://www.eclipse.org/paho/"
SECTION = "console/network"
LICENSE = "EPL-1.0 | EDL-1.0"

LIC_FILES_CHKSUM = " \
    file://src/mqtt/message.h;beginline=9;endline=18;md5=5eec304e6066523386c222963ceeb6ff \
    file://edl-v10;md5=3adfcc70f5aeb7a44f3f9b495aa1fbf3 \
    file://epl-v10;md5=659c8e92a40b6df1d9e3dccf5ae45a08 \
"

SRC_URI = "git://github.com/eclipse/paho.mqtt.cpp;protocol=https;branch=master \
           file://0001-cmake-Use-CMAKE_INSTALL_LIBDIR-and-CMAKE_INSTALL_BIN.patch \
           file://libpaho-mqtt3a.pc \
           file://libpaho-mqtt3as.pc \
           file://libpaho-mqtt3c.pc \
           file://libpaho-mqtt3cs.pc \
           file://libpaho-mqttpp3.pc \
"
SRCREV = "33921c8b68b351828650c36816e7ecf936764379"

do_install_append() {
    install -d ${D}/${includedir}/mqtt
    cp -a ${S}/src ${D}/${includedir}/mqtt
    install -d ${D}/${libdir}/pkgconfig
    install -m 644 ${WORKDIR}/libpaho-mqtt3a.pc ${D}/${libdir}/pkgconfig
    install -m 644 ${WORKDIR}/libpaho-mqtt3as.pc ${D}/${libdir}/pkgconfig
    install -m 644 ${WORKDIR}/libpaho-mqtt3c.pc ${D}/${libdir}/pkgconfig
    install -m 644 ${WORKDIR}/libpaho-mqtt3cs.pc ${D}/${libdir}/pkgconfig
    install -m 644 ${WORKDIR}/libpaho-mqttpp3.pc ${D}/${libdir}/pkgconfig
}

FILES_${PN}-dev += "${includedir}/mqtt/* ${libdir}/pkgconfig"
DEPENDS = "openssl paho-mqtt-c"

S = "${WORKDIR}/git"

inherit cmake


EXTRA_OECMAKE += "-DPAHO_WITH_SSL=ON"

BBCLASSEXTEND = "native nativesdk"
