SUMMARY = "Pure-python wrapper for libusb-1.0"
HOMEPAGE = "https://github.com/vpelletier/python-libusb1"
SECTION = "devel/python"

LICENSE = "LGPL-2.1-or-later & GPL-2.0"
LIC_FILES_CHKSUM = "\
    file://COPYING.LESSER;md5=4fbd65380cdd255951079008b364516c \
    file://COPYING;md5=751419260aa954499f7abaabaa882bbe \
"

SRC_URI[sha256sum] = "3951d360f2daf0e0eacf839e15d2d1d2f4f5e7830231eb3188eeffef2dd17bad"

inherit setuptools3 pypi