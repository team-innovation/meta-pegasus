DESCRIPTION = "A built-package format for Python."
HOMEPAGE = "https://pypi.org/project/wheel/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=9d66b41bc2a080e7174acc5dffecd752"

inherit pypi setuptools3

SRC_URI[md5sum] = "0ac15797d94ca759702b1d52425850c4"
SRC_URI[sha256sum] = "0a2e54558a0628f2145d2fc822137e322412115173e8a2ddbe1c9024338ae83c"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
