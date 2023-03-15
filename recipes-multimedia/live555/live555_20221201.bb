# live555 OE build file
# Copyright (C) 2005, Koninklijke Philips Electronics NV.  All Rights Reserved
# Released under the MIT license (see packages/COPYING)

DESCRIPTION = "LIVE555 Streaming Media libraries"
HOMEPAGE = "http://live.com/"
LICENSE = "LGPL-3.0"
SECTION = "devel"
PR = "r19"

DEPENDS = "openssl"

URLV = "${@d.getVar('PV')[0:4]}.${@d.getVar('PV')[4:6]}.${@d.getVar('PV')[6:8]}"

#SRC_URI = "https://download.videolan.org/pub/contrib/live555/live.${URLV}.tar.gz 

SRC_URI = "https://builds-archive.vivint.com/innovation/downloads/live.${URLV}.tar.gz \
        file://0001-Vivint-changes-for-20221001.patch \
        file://0002-VID-7204-fix-video-preload-issue.patch \
        file://0003-Play-sdp-packages.patch \
        file://0004-Add-keyData-injection.patch \
        file://TLSprotected.patch \
"

# only latest live version stays on http://www.live555.com/liveMedia/public/, add mirror for older
MIRRORS += "http://www.live555.com/liveMedia/public/ http://download.videolan.org/contrib/live555/ \n"

SRC_URI[md5sum] = "c08ae78acbb1f2cf78b7aa1dc362eb7a"
SRC_URI[sha256sum] = "057c1d3dc24c26b33e14c4dc3592885adf220403a1e1255e8a101e233c69c108"

S = "${WORKDIR}/live"

LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504 \
                    file://COPYING.LESSER;md5=e6a600fd5e1d9cbde2d983680233ad02 \
                   "

TARGET_CC_ARCH += "${LDFLAGS}"

do_configure() {
    chmod a+w config.linux-with-shared-libraries
    sed -i -e "s:-I/usr/local/include::" config.linux-with-shared-libraries
    echo "COMPILE_OPTS+="-DALLOW_RTSP_SERVER_PORT_REUSE=1  -DDEBUG_SEND=1 \
    -DRTPINTERFACE_BLOCKING_WRITE_TIMEOUT_MS=5000 -DPREFER_LOW_LATENCY=1 -DCORK_PAYLOAD=1 \
    -DXLOCALE_NOT_USED -DDEBUG"" >> config.linux-with-shared-libraries
    ./genMakefiles linux-with-shared-libraries
}

do_install() {
    install -d ${D}${includedir}/BasicUsageEnvironment
    install -d ${D}${includedir}/groupsock
    install -d ${D}${includedir}/liveMedia
    install -d ${D}${includedir}/UsageEnvironment
    install -d ${D}${libdir}
    oe_runmake install DESTDIR=${D} PREFIX=/usr
    cp -R --no-dereference --preserve=mode,links -v ${S}/BasicUsageEnvironment/include/*.hh ${D}${includedir}/BasicUsageEnvironment/
    cp -R --no-dereference --preserve=mode,links -v ${S}/groupsock/include/*.h ${D}${includedir}/groupsock/
    cp -R --no-dereference --preserve=mode,links -v ${S}/groupsock/include/*.hh ${D}${includedir}/groupsock/
    cp -R --no-dereference --preserve=mode,links -v ${S}/liveMedia/include/*.hh ${D}${includedir}/liveMedia/
    cp -R --no-dereference --preserve=mode,links -v ${S}/UsageEnvironment/include/*.hh ${D}${includedir}/UsageEnvironment/
    # Find all the headers
    rm -fr ${S}/.pc
    for i in $(find . -name "*.hh") $(find . -name "*.h") ; do
        install ${i} ${D}${includedir}
    done
    install -d ${D}${bindir}
    for i in MPEG2TransportStreamIndexer openRTSP playSIP sapWatch testMPEG1or2ProgramToTransportStream testMPEG1or2Splitter testMPEG1or2VideoReceiver testMPEG2TransportStreamTrickPlay testOnDemandRTSPServer testRelay testAMRAudioStreamer testDVVideoStreamer testMP3Receiver testMP3Streamer testMPEG1or2AudioVideoStreamer testMPEG1or2VideoStreamer testMPEG2TransportStreamer testMPEG4VideoStreamer testWAVAudioStreamer vobStreamer; do
        install -m 0755 ${S}/testProgs/${i} ${D}${bindir}/
    done
    install -m 0755 ${S}/mediaServer/live555MediaServer ${D}${bindir}/
}

RDEPENDS_${PN}-dev = ""
PACKAGES =+ "live555-openrtsp live555-playsip live555-mediaserver live555-examples"
PACKAGES =+ "live555-proxyserver live555-test live555-libusageenvironment" 
PACKAGES =+ "live555-libbasicusageenvironment live555-libgroupsock live555-liblivemedia"
PACKAGES =+ "live555-libbasicusageenvironment-dev live555-libgroupsock-dev live555-liblivemedia-dev"
FILES_live555-openrtsp = "${bindir}/openRTSP"
FILES_live555-playsip = "${bindir}/playSIP"
FILES_live555-mediaserver = "${bindir}/live555MediaServer"
FILES_live555-examples = "${bindir}/*"
FILES_live555-test = "${bindir}/test*"
FILES_live555-libusageenvironment = "${libdir}/libUsageEnvironment.so.*"
FILES_live555-libbasicusageenvironment = "${libdir}/libBasicUsageEnvironment.so.*"
FILES_live555-libgroupsock = "${libdir}/libgroupsock.so.*"
FILES_live555-liblivemedia = "${libdir}/libliveMedia.so.*"
