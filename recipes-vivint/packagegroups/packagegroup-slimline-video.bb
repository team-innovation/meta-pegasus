# Copyright (C) 2015 Vivint, Inc.

DESCRIPTION = "touchlink video packages, imx firmware, gst plugins, etc"
LICENSE = "MIT"
LICENSE_FLAGS_WHITELIST = "commercial license"

PR = "r5"

inherit packagegroup

RPROVIDES_${PN} = " \
	libglib-2.0-utils \
	libgstapp-0.10-0 \
	libgstnetbuffer-0.10-0 \
	libgstrtp-0.10-0 \
	libgstrtsp-0.10-0 \
	libgstsdp-0.10-0 \
	libgstvideo-0.10-0 \
	libgudev-1.0-0 \
	libgstpbutils-1.0 \
	libgstvideo-1.0 \
	libgstapp-1.0 \
	libgstaudio-1.0 \
	libgudev-1.0-0 \
	libg2d0.8 \
"

RDEPENDS_${PN} = " \
	gstreamer-imx \
	firmware-imx \
	gst-fsl-plugin \
	gst-plugins-good-id3demux \
	imx-lib \
	libfslcodec \
	libfslparser \
	gstreamer \
	gst-plugins-base \
	gst-plugins-good \
	gst-plugins-bad \
	gst-plugins-ugly \
	gst-plugins-good-autodetect \
	gst-plugins-good-audiofx \
	gst-plugins-good-avi \
	gst-plugins-good-jpeg \
	gst-plugins-good-id3demux \
	gst-plugins-good-pulse \
	gst-plugins-good-rtpmanager \
	gst-plugins-good-rtp \
	gst-plugins-good-rtsp \
	gst-plugins-good-alaw \
	gst-plugins-good-mulaw \
	gst-plugins-good-isomp4 \
	gst-plugins-good-udp \
	gst-plugins-good-video4linux2 \
	gst-plugins-base-audioconvert \
	gst-plugins-base-audioresample \
	gst-plugins-base-decodebin2 \
	gst-plugins-base-ffmpegcolorspace \
	gst-plugins-base-playbin \
	gst-plugins-base-videotestsrc \
	gst-plugins-bad-colorspace \
	gst-plugins-bad-h264parse \
	gst-plugins-bad-mpegtsmux \
	gst-plugins-ugly-mad \
	gst-plugins-ugly-lame \
	libglib-2.0-utils \
	libgstapp-0.10-0 \
	libgstnetbuffer-0.10-0 \
	libgstrtp-0.10-0 \
	libgstrtsp-0.10-0 \
	libgstsdp-0.10-0 \
	libgstvideo-0.10-0 \
	gstreamer1.0-plugins-base \
	gstreamer1.0-plugins-good \
	gstreamer1.0-plugins-bad \
	gstreamer1.0-plugins-base-playback \
	gstreamer1.0-plugins-base-typefindfunctions \
	gstreamer1.0-plugins-base-videotestsrc \
	gstreamer1.0-plugins-good-jpeg \
	gstreamer1.0-plugins-good-isomp4 \
	gstreamer1.0-plugins-good-rtp \
	gstreamer1.0-plugins-good-rtpmanager \
	gstreamer1.0-plugins-good-rtsp \
	gstreamer1.0-plugins-good-udp \
	gstreamer1.0-plugins-good-video4linux2 \
	gstreamer1.0-plugins-bad-videoparsersbad \
	gstreamer1.0-rtsp-server \
	gstreamer-imx-imxipu \
	gstreamer-imx-imxaudio \
	gstreamer-imx-meta \
	gstreamer-imx-imxpxp \
	gstreamer-imx-imxeglvivsink \
	gstreamer-imx \
	gstreamer-imx-imxg2d \
	gstreamer-imx-imxv4l2videosrc \
	gstreamer-imx-imxvpu \
	libgstrtp-1.0-0 \
	libgstrtsp-1.0-0 \
	libgstsdp-1.0-0 \
	libgsturidownloader-1.0-0 \
	libgstimxcommon0 \
	libgstimxcompositor0 \
	libgstimxblitter0 \
	libgstriff-1.0-0 \
	libgstcodecparsers-1.0-0 \
	libgstrtspserver-1.0-0 \
"
