# Copyright (C) 2015 Vivint, Inc.

DESCRIPTION = "touchlink video packages, imx firmware, gst plugins, etc"
LICENSE = "MIT"
LICENSE_FLAGS_WHITELIST = "commercial license"

PR = "r20"

inherit packagegroup

RPROVIDES_${PN} = " \
	libg2d0.8 \
	libglib-2.0-utils \
	libgstapp-1.0 \
	libgstaudio-1.0 \
	libgstpbutils-1.0 \
	libgstvideo-1.0 \
	libgudev-1.0-0 \
	libopus0 \
"

RDEPENDS_${PN} = " \
	firmware-imx \
	gstreamer1.0-plugins-bad \
	gstreamer1.0-plugins-bad-videoparsersbad \
	gstreamer1.0-plugins-base \
	gstreamer1.0-plugins-base-audioconvert \
	gstreamer1.0-plugins-base-audioresample \
	gstreamer1.0-plugins-base-playback \
	gstreamer1.0-plugins-base-tcp \
	gstreamer1.0-plugins-base-ogg \
	gstreamer1.0-plugins-base-typefindfunctions \
	gstreamer1.0-plugins-base-videoconvert \
	gstreamer1.0-plugins-base-videotestsrc \
	gstreamer1.0-plugins-base-volume \
	gstreamer1.0-plugins-bad-opus \
	gstreamer1.0-plugins-good \
	gstreamer1.0-plugins-good-imagefreeze \
	gstreamer1.0-plugins-good-isomp4 \
	gstreamer1.0-plugins-good-jpeg \
	gstreamer1.0-plugins-good-level \
	gstreamer1.0-plugins-good-mulaw \
	gstreamer1.0-plugins-good-pulse \
	gstreamer1.0-plugins-good-rtp \
	gstreamer1.0-plugins-good-rtpmanager \
	gstreamer1.0-plugins-good-rtsp \
	gstreamer1.0-plugins-good-udp \
	gstreamer1.0-plugins-good-video4linux2 \
	gstreamer1.0-plugins-good-wavenc \
	gstreamer1.0-plugins-good-wavparse \
	gstreamer1.0-rtsp-server \
        gstreamer-imx \
        gstreamer-imx-imxaudio \
        gstreamer-imx-imxeglvivsink \
        gstreamer-imx-imxg2d \
        gstreamer-imx-imxipu \
        gstreamer-imx-imxpxp \
        gstreamer-imx-imxv4l2videosrc \
        gstreamer-imx-imxvpu \
        gstreamer-imx-meta \
	imx-lib \
	ffmpeg \
	libfslcodec \
	libfslparser \
	libglib-2.0-utils \
	libgstcodecparsers-1.0-0 \
	libgstimxblitter0 \
	libgstimxcommon0 \
	libgstimxcompositor0 \
	libgstriff-1.0-0 \
	libgstrtp-1.0-0 \
	libgstrtsp-1.0-0 \
	libgstrtspserver-1.0-0 \
	libgstsdp-1.0-0 \
	libgsturidownloader-1.0-0 \
	libopus \
	civetweb \
"
