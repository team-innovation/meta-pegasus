SUMMARY = "C library for encoding data in a QR Code symbol"
AUTHOR = "Kentaro Fukuchi"
HOMEPAGE = "http://fukuchi.org/works/qrencode/"
SECTION = "libs"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=2d5025d4aa3495befef8f17206a5b0a1"
PV = "4.0.0+git${SRCPV}"

SRCREV = "0020ab6c8a22ef6eba3c6164b20d1fc6daa4cfcb"
SRC_URI = "git://github.com/fukuchi/libqrencode.git;branch=4.0;protocol=https"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

EXTRA_OECONF += "--without-tools --without-tests"
