# Copyright (C) 2015 Vivint, Inc.

DESCRIPTION = "touchlink video packages, imx firmware, gst plugins, etc"
LICENSE = "MIT"
LICENSE_FLAGS_WHITELIST = "commercial license"

PR = "r21"

inherit packagegroup


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
	gst1.0-fsl-plugin \
	gst1.0-fsl-plugin-gplay \
	gst1.0-fsl-plugin-grecorder \
	imx-lib \
	ffmpeg \
	libfslcodec \
	libfslparser \
	libgstcodecparsers-1.0-0 \
	libgstriff-1.0-0 \
	libgstrtp-1.0-0 \
	libgstrtsp-1.0-0 \
	libgstrtspserver-1.0-0 \
	libgstsdp-1.0-0 \
	libgsturidownloader-1.0-0 \
	libopus \
	civetweb \
"
