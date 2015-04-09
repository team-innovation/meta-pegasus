###
### Change DVRD_PREPEND everything you touch this script
###
DVRD_PREPEND = "-11"

DVRD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-dvrd = "${PR}${DVRD_PREPEND}${DVRD_REVISION}"
 
OLD_TL_RDEPENDS_${PN}-dvrd =   "\
	gst-openmax             \
	ti-omx                  \
	gst-rtsp-server         \
        ffmpeg                  \
"

RDEPENDS_${PN}-dvrd = "\
        gstreamer                             \
        gst-plugins-good-multifile            \
        gst-plugins-good-avi                  \
        gst-plugins-good-adder                \
        gst-plugins-good-jpeg                 \
        gst-plugins-good-app                  \
        gst-plugins-good-audioconvert         \
        gst-plugins-good-audiofx              \
        gst-plugins-good-audioresample        \
        gst-plugins-good-rtpmanager           \
        gst-plugins-good-spectrum             \
        gst-plugins-good-colorspace           \
        gst-plugins-good-typefindfunctions    \
        gst-plugins-good-deinterlace          \
        gst-plugins-good-udp                  \
        gst-plugins-good-faac                 \
        gst-plugins-good-videobox             \
        gst-plugins-good-faad                 \
        gst-plugins-good-videomixer           \
        gst-plugins-good-videoscale           \
        gst-plugins-good-gio                  \
        gst-plugins-good-videotestsrc         \
        gst-plugins-good-goom                 \
        gst-plugins-good-volume               \
        gst-plugins-good-id3tag               \
        gst-plugins-good-lame                 \
        gst-plugins-good-mad                  \
        gst-plugins-bad-h264parse             \
        gst-plugins-bad-rtp                   \
        gst-plugins-good-rtsp                 \
        gst-plugins-bad-video4linux2          \
        gst-plugins-bad-playbin               \
        gst-plugins-bad-ffmpegcolorspace      \
        gst-plugins-bad-autodetect            \
        gst-plugins-bad-decodebin2            \
        gst-plugins-bad-mulaw                 \
        gst-plugins-bad-alaw                  \
        gst-plugins-bad-isomp4                \
        gst-ffmpeg                            \
"

do_compile_prepend() {

        # compile dvrd C code
        make -C ${S}/code/dvrd/libdvr       \                     
              GST_INCLUDE_DIR=${STAGING_DIR}/${HOST_ARCH}v7a${HOST_VENDOR}-${HOST_OS}/usr/include/gstreamer-0.10 \
              GLIB2_INC_DIR=${STAGING_DIR}/${HOST_ARCH}v7a${HOST_VENDOR}-${HOST_OS}/usr/include/glib-2.0 \
              GLIB2_LIB_DIR=${STAGING_DIR}/${HOST_ARCH}v7a${HOST_VENDOR}-${HOST_OS}/usr/lib/glib-2.0/include \
              LIBXML_INCLUDE_FOR_OE=-I${STAGING_DIR}/${HOST_ARCH}v7a${HOST_VENDOR}-${HOST_OS}/usr/include/libxml2 \
              LINUX_PATH=${STAGING_KERNEL_DIR}  \
              LIBS=-L${STAGING_DIR}/${HOST_ARCH}v7a${HOST_VENDOR}-${HOST_OS}/usr/lib 
}

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/dvrd/
	${RSYNC_CMD} ${S}/code/dvrd/ ${D}/${INSTALL_DIR}/dvrd/
	chmod +x ${D}/${INSTALL_DIR}/dvrd/dvrd.pyo

	# dvrd library and binary
        install -d ${D}/${INSTALL_DIR}/lib
        cp -a ${S}/code/dvrd/libdvr/libdvr.so ${D}/${INSTALL_DIR}/lib

	# remove dvr libdvr dir
        rm -rf ${D}/${INSTALL_DIR}/dvrd/libdvr
}


FILES_${PN}-dvrd = "\
       ${INSTALL_DIR}/dvrd/*   \
       ${INSTALL_DIR}/lib/libdvr.so	\
	${sysconfdir}/init.d/dvrd \
	${sysconfdir}/procman.d/dvrd \
       "
             
INSANE_SKIP_${PN}-dvrd = "True"
