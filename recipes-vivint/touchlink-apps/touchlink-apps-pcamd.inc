###
### Change PCAMD_PREPEND everything you touch this script
###
PCAMD_PREPEND = "-1"

PCAMD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-pcamd = "${PR}${PCAMD_PREPEND}${PCAMD_REVISION}"

OLD_TL_RDEPENDS_${PN}-pcamd =   "\
	gst-openmax             \
	ti-omx                  \
	gst-rtsp-server         \
        ffmpeg                  \
"

RDEPENDS_${PN}-pcamd = "\
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
        # compile pcamd C code
        ln -sf ${STAGING_DIR}/${MACHINE}/usr/include/gnu/stubs-hard.h ${STAGING_DIR}/${MACHINE}/usr/include/gnu/stubs-soft.h
        make -C ${S}/code/pcamd/libpcam SYSTEM="SLIMLINE" \
              GST_DIR=${STAGING_DIR}/${MACHINE}/usr
}

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/pcamd/
	${RSYNC_CMD} ${S}/code/pcamd/ ${D}/${INSTALL_DIR}/pcamd/
	chmod +x ${D}/${INSTALL_DIR}/pcamd/pcamd.pyo

	# pcamd library and binary
        install -d ${D}/${INSTALL_DIR}/lib
        cp -a ${S}/code/pcamd/libpcam/libpcam.so ${D}/${INSTALL_DIR}/lib

	# remove pcam libpcam dir
        rm -rf ${D}/${INSTALL_DIR}/pcamd/libpcam
}


FILES_${PN}-pcamd = " \
       ${INSTALL_DIR}/pcamd/*   \
       ${INSTALL_DIR}/lib/libpcam.so	\
	${sysconfdir}/init.d/pcamd \
	${sysconfdir}/procman.d/pcamd \
       "

INSANE_SKIP_${PN}-pcamd = "installed-vs-shipped"
