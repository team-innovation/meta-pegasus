require ${PN}.inc

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=2d5025d4aa3495befef8f17206a5b0a1"
SECTION = "multimedia"

SRC_URI = " \
    git://github.com/GStreamer/qt-gstreamer;protocol=https \
    file://0001-Added-i.MX6-zero-copy-rendering-implementation.patch \
	file://0002-Set-playbin-s-flags-as-native-video-audio-in-case-of.patch \
	file://0003-qtgstreamer-build-fix.patch \
"
SRCREV = "2a9664aff4980380afb93720795d326dfc76785f"
S = "${WORKDIR}/git"

FILES_${PN} += "\
    ${libdir}/gstreamer-1.0/* \
"

FILES_${PN}-dbg += "\
    ${libdir}/gstreamer-1.0/.debug/* \
"

FILES_${PN}-examples += "\
    ${libdir}/gt5/examples/${PN}/* \
"

inherit cmake_qt5

export EXTRA_OECMAKE = "-DQT_VERSION=5 \
                        -DHW_PLATFORM=imx6 \
                        -DOE_QMAKE_PATH_EXTERNAL_HOST_BINS=${STAGING_DIR_NATIVE}/usr/bin/qt5/ \
                        -DUSE_QT_PLUGIN_DIR=OFF \
                        -DCMAKE_SKIP_INSTALL_RPATH=YES \
                        -DCMAKE_SKIP_RPATH=YES \
"

EXTRA_OECONF += "--disable-rpath"

do_install_append() {
#    install ${B}/examples/qmlplayer2/qmlplayer2 ${D}/usr/share/qt5/examples/qt-gstreamer/
    install -d ${D}${datadir}/qt5/examples/${P}
    install -m 0755 ${B}/examples/qmlplayer2/qmlplayer2 ${D}${datadir}/qt5/examples/${P}
}

RDEPENDS_${PN}-examples += " \
    ${PN}-qmlplugins \
    qtquick1 \
    qtquickcontrols-qmlplugins \
    qtdeclarative-qmlplugins \
    gstreamer1.0 \
    libgstpbutils-1.0 \
    libgstvideo-1.0 \
    libgstapp-1.0 \
    libgstaudio-1.0 \
"
