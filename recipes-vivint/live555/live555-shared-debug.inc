# live555 OE build file
# Copyright (C) 2013, Vivint
# 

DESCRIPTION = "LIVE555 Streaming Media libraries"
HOMEPAGE = "http://live.com/"
LICENSE = "LGPL"
SECTION = "devel"

INC_PR = "r31"

MODULE = "live555"
SRC_URI = "file:///home/craig/hg/live555 \
	       file://config.linux-cross-shared-libraries-debug"


S = "${WORKDIR}/${MODULE}"
TARGET_CC_ARCH += "${LDFLAGS}"

do_configure() {
	cp ${WORKDIR}/config.linux-cross-shared-libraries-debug .
	./genMakefiles linux-cross-shared-libraries-debug
}

do_compile() {
	make
}

do_install() {
	make install DESTDIR=${D} PREFIX=/usr
	install -d ${D}${includedir}/BasicUsageEnvironment
	install -d ${D}${includedir}/groupsock
	install -d ${D}${includedir}/liveMedia
	install -d ${D}${includedir}/UsageEnvironment
	install -d ${D}${libdir}
	cp -a ${S}/BasicUsageEnvironment/include/*.hh ${D}${includedir}/BasicUsageEnvironment/
	cp -a ${S}/groupsock/include/*.h ${D}${includedir}/groupsock/
	cp -a ${S}/groupsock/include/*.hh ${D}${includedir}/groupsock/
	cp -a ${S}/liveMedia/include/*.hh ${D}${includedir}/liveMedia/
	cp -a ${S}/UsageEnvironment/include/*.hh ${D}${includedir}/UsageEnvironment/
	# Find all the headers
	for i in $(find . -name "*.hh") $(find . -name "*.h") ; do
		install ${i} ${D}${includedir}
	done

	echo ${D} > /tmp/t
	cp ${S}/*/*.so.* ${D}${libdir}
	install -d ${D}${bindir}
	for i in MPEG2TransportStreamIndexer openRTSP playSIP sapWatch testMPEG1or2ProgramToTransportStream testMPEG1or2Splitter testMPEG1or2VideoReceiver testMPEG2TransportStreamTrickPlay testOnDemandRTSPServer testRelay testAMRAudioStreamer testDVVideoStreamer testMP3Receiver testMP3Streamer testMPEG1or2AudioVideoStreamer testMPEG1or2VideoStreamer testMPEG2TransportStreamer testMPEG4VideoStreamer testWAVAudioStreamer vobStreamer; do
		install -m 0755 ${S}/testProgs/${i} ${D}${bindir}/
	done
	install -m 0755 ${S}/mediaServer/live555MediaServer ${D}${bindir}/
	install -m 0755 ${S}/proxyServer/live555ProxyServer ${D}${bindir}/
}

PACKAGES += "live555-openrtsp live555-playsip live555-mediaserver live555-proxyserver live555-test"
PACKAGES += "live555-libUsageEnvironment live555-libBasicUsageEnvironment live555-libgroupsock live555-libliveMedia"
FILES_${PN} = "${bindir}/sapWatch ${bindir}/testMPEG1or2ProgramToTransportStream ${bindir}/testMPEG1or2Splitter ${bindir}/testMPEG1or2VideoReceiver ${bindir}/testMPEG2TransportStreamTrickPlay ${bindir}/testOnDemandRTSPServer ${bindir}/testRelay ${bindir}/testAMRAudioStreamer ${bindir}/testDVVideoStreamer ${bindir}/testMP3Receiver ${bindir}/testMP3Streamer ${bindir}/testMPEG1or2AudioVideoStreamer ${bindir}/testMPEG1or2VideoStreamer ${bindir}/testMPEG2TransportStreamer ${bindir}/testMPEG4VideoStreamer ${bindir}/testWAVAudioStreamer ${bindir}/vobStreamer ${bindir}/MPEG2TransportStreamIndexer"
FILES_live555-openrtsp = "${bindir}/openRTSP"
FILES_live555-playsip = "${bindir}/playSIP"
FILES_live555-mediaserver = "${bindir}/live555MediaServer"
FILES_live555-proxyserver = "${bindir}/live555ProxyServer"
FILES_live555-test = "${bindir}/test* ${bindir}/registerRTSPStream"

FILES_live555-libUsageEnvironment = "${libdir}/libUsageEnvironment.so.*"
FILES_live555-libBasicUsageEnvironment = "${libdir}/libBasicUsageEnvironment.so.*"
FILES_live555-libgroupsock = "${libdir}/libgroupsock.so.*"
FILES_live555-libliveMedia = "${libdir}/libliveMedia.so.*"

