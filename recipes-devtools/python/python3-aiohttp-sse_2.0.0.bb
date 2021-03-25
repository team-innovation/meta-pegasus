DESCRIPTION = "Server-sent events support for aiohttp"
HOMEPAGE = "https://pypi.org/project/aiohttp-sse/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d2794c0df5b907fdace235a619d80314"

DEPENDS += "python3-aiohttp"

PR = "r1"

SRC_URI[md5sum] = "d2f394fb75f591045b2c94fc17c8533d"
SRC_URI[sha256sum] = "547e1eaa129749f090d02b31956215edbcde74ce99721f5f0ac902a9ccb1202e"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"


inherit setuptools3 pypi
