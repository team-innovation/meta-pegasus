DESCRIPTION = "cChardet is high speed universal character encoding detector."
HOMEPAGE = "https://pypi.org/project/cchardet/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=6ecda54f6f525388d71d6b3cd92f7474"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit setuptools3 pypi

SRC_URI[md5sum] = "bbfb26239b5129e93c8812efcc54d935"
SRC_URI[sha256sum] = "9c9269208b9f8d7446dbd970f6544ce48104096efab0f769ee5918066ba1ee7e"

