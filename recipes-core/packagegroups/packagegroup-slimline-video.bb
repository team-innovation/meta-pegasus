# Copyright (C) 2015 Vivint, Inc.

DESCRIPTION = "touchlink video packages, imx firmware, gst plugins, etc"
LICENSE = "MIT"

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
"

RDEPENDS_${PN} = " \
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
    gst-plugins-good-id3demux \
    gst-plugins-good-pulse \
    gst-plugins-good-rtpmanager \
    gst-plugins-good-rtp \
    gst-plugins-good-rtsp \
    gst-plugins-good-alaw \
    gst-plugins-good-mulaw \
    gst-plugins-good-isomp4 \
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
	libgudev-1.0-0 \
"
