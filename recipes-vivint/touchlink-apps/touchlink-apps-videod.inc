###
### Change VIDEOD_PREPEND everything you touch this script
###
VIDEOD_PREPEND = "-16"

VIDEOD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-videod = "${PR}${VIDEOD_PREPEND}${VIDEOD_REVISION}"

OLD_TL_RDEPENDS_${PN}-videod = "\
	gst-openmax             \
	ti-omx                  \
	gst-rtsp-server		\
	ffmpeg                  \
	gstreamer                             \
	gst-plugins-good-adder                \
	gst-plugins-good-app                  \
	gst-plugins-good-audioconvert         \
	gst-plugins-good-audiofx              \
	gst-plugins-good-audioresample        \
	gst-plugins-good-avi                  \
	gst-plugins-good-colorspace           \
	gst-plugins-good-deinterlace          \
	gst-plugins-good-faac                 \
	gst-plugins-good-faad                 \
	gst-plugins-good-gio                  \
	gst-plugins-good-goom                 \
	gst-plugins-good-id3tag               \
	gst-plugins-good-jpeg                 \
	gst-plugins-good-lame                 \
	gst-plugins-good-mad                  \
	gst-plugins-good-multifile            \
	gst-plugins-good-rtpmanager           \
	gst-plugins-good-rtsp                 \
	gst-plugins-good-spectrum             \
	gst-plugins-good-typefindfunctions    \
	gst-plugins-good-udp                  \
	gst-plugins-good-videobox             \
	gst-plugins-good-videomixer           \
	gst-plugins-good-videoscale           \
	gst-plugins-good-videotestsrc         \
	gst-plugins-good-volume               \
	gst-plugins-bad-alaw                  \
	gst-plugins-bad-ffmpegcolorspace      \
	gst-plugins-bad-autodetect            \
	gst-plugins-bad-decodebin2            \
	gst-plugins-bad-h264parse             \
	gst-plugins-bad-isomp4                \
	gst-plugins-bad-mulaw                 \
	gst-plugins-bad-playbin               \
	gst-plugins-bad-rtp                   \
	gst-plugins-bad-video4linux2          \
	gst-ffmpeg                            \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/videod/
	${RSYNC_CMD} ${S}/code/videod/ ${D}/${INSTALL_DIR}/videod/
	chmod +x ${D}/${INSTALL_DIR}/videod/videod.pyo
}


FILES_${PN}-videod = "\
	${INSTALL_DIR}/videod/*   \
	${sysconfdir}/init.d/videod \
	${sysconfdir}/procman.d/videod \
       "

INSANE_SKIP_${PN}-videod = "True"
