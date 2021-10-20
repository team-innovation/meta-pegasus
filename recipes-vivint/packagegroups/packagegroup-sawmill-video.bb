# Copyright (C) 2015 Vivint, Inc.

DESCRIPTION = "touchlink video packages, imx firmware, gst plugins, etc"
LICENSE = "MIT"
LICENSE_FLAGS_WHITELIST = "commercial license"

PR = "r20"

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
	gstreamer1.0-plugins-base-opus \
	gstreamer1.0-plugins-bad-opusparse \
	gstreamer1.0-plugins-base-typefindfunctions \
	gstreamer1.0-plugins-base-videoconvert \
	gstreamer1.0-plugins-base-videotestsrc \
	gstreamer1.0-plugins-base-videoscale \
	gstreamer1.0-plugins-base-volume \
	gstreamer1.0-plugins-good \
	gstreamer1.0-plugins-good-imagefreeze \
	gstreamer1.0-plugins-good-isomp4 \
	gstreamer1.0-plugins-good-jpeg \
	gstreamer1.0-plugins-good-level \
	gstreamer1.0-plugins-good-mulaw \
	gstreamer1.0-plugins-good-rtp \
	gstreamer1.0-plugins-good-rtpmanager \
	gstreamer1.0-plugins-good-rtsp \
	gstreamer1.0-plugins-good-udp \
	gstreamer1.0-plugins-base-gio \
	gstreamer1.0-plugins-good-wavenc \
	gstreamer1.0-plugins-good-wavparse \
	gstreamer1.0-plugins-good-videofilter \
	gstreamer1.0-plugins-good-autodetect \
	gstreamer1.0-plugins-base-alsa \
	gstreamer1.0-plugins-good-pulseaudio \
	gstreamer1.0-rtsp-server \
	imx-gst1.0-plugin \
	imx-gst1.0-plugin-gplay \
	imx-gst1.0-plugin-grecorder \
	imx-lib \
	ffmpeg \
	imx-codec \
	imx-parser \
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
