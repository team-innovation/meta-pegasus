DESCRIPTION = "GStreamer plugin rtsp server"
HOMEPAGE = "http://"
LICENSE = "LGPL"
DEPENDS = "gstreamer gst-plugins-base gst-plugins-good"
PR = "r1"

SRCREV = "ed7bb93c2ff6c35229687604f6d6ae8c1ff3c4f2"
SRC_URI = "git://anongit.freedesktop.org/gstreamer/gst-rtsp-server;protocol=git;branch=master"
S = "${WORKDIR}/git"

inherit autotools

do_install() {	
	install -d ${D}${libdir}/
	cp  -a ${S}/gst/rtsp-server/.libs/libgstrtspserver*.so* ${D}${libdir}/

	# deploy header file
	install -d ${STAGING_DIR}/${MACHINE}/usr/include/gstreamer-0.10/gst/rtsp-server
	cp  -a ${S}/gst/rtsp-server/rtsp-server.h ${STAGING_DIR}/${MACHINE}/usr/include/gstreamer-0.10/gst/rtsp-server
}


FILES_${PN} = "${libdir}/libgstrtspserver*.so*"

SRC_URI[md5sum] = "b511af07000595f63c3a705946221643"
SRC_URI[sha256sum] = "9915887cf8515bda87462c69738646afb715b597613edc7340477ccab63a6617"

do_qa_configure () {
	echo "No QA configure"
}

do_configure() {
	cd ${S}
    ./autogen.sh \
      --disable-gtk-doc \
      --host=${TARGET_SYS} \
      --prefix=/usr --disable-po
    ./configure --host=${TARGET_SYS} --prefix=/usr --disable-po
}

do_compile () {
	cd ${S}
	make
}

#INSANE_SKIP_${PN} = True
