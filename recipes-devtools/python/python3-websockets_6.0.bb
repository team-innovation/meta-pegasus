SUMMARY = "Websockets"
HOMEPAGE = "https://pypi.org/project/websockets"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5070256738c06d2e59adbec1f4057dac"

inherit setuptools3 pypi

SRC_URI[md5sum] = "76cf931a525a3415f5a4f59c133e89c3"
SRC_URI[sha256sum] = "8f3b956d11c5b301206382726210dc1d3bee1a9ccf7aadf895aaf31f71c3716c"


# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

