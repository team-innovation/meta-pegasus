# live555 OE build file
# Copyright (C) 2005, Koninklijke Philips Electronics NV.  All Rights Reserved
# Released under the MIT license (see packages/COPYING)

DESCRIPTION = "LIVE555 Streaming Media libraries"
HOMEPAGE = "http://live.com/"
LICENSE = "LGPLv3"
SECTION = "devel"

DEPENDS = "openssl"

SRCREV = "${GIT_LIVE555_REV}"
SRCBRANCH = "nextgenhub"
SRC_URI = "git://${GIT_LIVE555_SERVER}/${MODULE};protocol=${GIT_LIVE555_PROTOCOL};branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504 \
                    file://COPYING.LESSER;md5=e6a600fd5e1d9cbde2d983680233ad02 \
                   "

TARGET_CC_ARCH += "${LDFLAGS}"

do_configure() {
    ./genMakefiles linux-with-shared-libraries
}

do_install() {
    make install DESTDIR=${D} PREFIX=/usr 
    install -d ${D}${includedir}/BasicUsageEnvironment
    install -d ${D}${includedir}/groupsock
    install -d ${D}${includedir}/liveMedia
    install -d ${D}${includedir}/UsageEnvironment
    install -d ${D}${libdir}
    cp -R --no-dereference --preserve=mode,links -v ${S}/BasicUsageEnvironment/include/*.hh ${D}${includedir}/BasicUsageEnvironment/
    cp -R --no-dereference --preserve=mode,links -v ${S}/groupsock/include/*.h ${D}${includedir}/groupsock/
    cp -R --no-dereference --preserve=mode,links -v ${S}/groupsock/include/*.hh ${D}${includedir}/groupsock/
    cp -R --no-dereference --preserve=mode,links -v ${S}/liveMedia/include/*.hh ${D}${includedir}/liveMedia/
    cp -R --no-dereference --preserve=mode,links -v ${S}/UsageEnvironment/include/*.hh ${D}${includedir}/UsageEnvironment/
    # Find all the headers
    for i in $(find . -name "*.hh") $(find . -name "*.h") ; do
        install ${i} ${D}${includedir}
    done
    cp ${S}/*/*.a ${D}${libdir}
    install -d ${D}${bindir}
    for i in MPEG2TransportStreamIndexer openRTSP playSIP sapWatch testMPEG1or2ProgramToTransportStream testMPEG1or2Splitter testMPEG1or2VideoReceiver testMPEG2TransportStreamTrickPlay testOnDemandRTSPServer testRelay testAMRAudioStreamer testDVVideoStreamer testMP3Receiver testMP3Streamer testMPEG1or2AudioVideoStreamer testMPEG1or2VideoStreamer testMPEG2TransportStreamer testMPEG4VideoStreamer testWAVAudioStreamer vobStreamer; do
        install -m 0755 ${S}/testProgs/${i} ${D}${bindir}/
    done
    install -m 0755 ${S}/mediaServer/live555MediaServer ${D}${bindir}/
}

PACKAGES =+ "live555-openrtsp live555-playsip live555-mediaserver live555-examples"
PACKAGES =+ "live555-proxyserver live555-test live555-libusageenvironment" 
PACKAGES =+ "live555-libbasicusageenvironment live555-libgroupsock live555-liblivemedia"
FILES_live555-openrtsp = "${bindir}/openRTSP"
FILES_live555-playsip = "${bindir}/playSIP"
FILES_live555-mediaserver = "${bindir}/live555MediaServer"
FILES_live555-examples = "${bindir}/*"
FILES_live555-test = "${bindir}/test*"

FILES_live555-libusageenvironment = "${libdir}/libUsageEnvironment.so.*"
FILES_live555-libbasicusageenvironment = "${libdir}/libBasicUsageEnvironment.so.*"
FILES_live555-libgroupsock = "${libdir}/libgroupsock.so.*"
FILES_live555-liblivemedia = "${libdir}/libliveMedia.so.*"

