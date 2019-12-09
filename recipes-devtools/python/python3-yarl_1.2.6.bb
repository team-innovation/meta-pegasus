DESCRIPTION = "Yet another URL library"
HOMEPAGE = "https://pypi.org/project/yarl/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b334fc90d45983db318f54fd5bf6c90b"

inherit pypi setuptools3

SRC_URI[md5sum] = "43380667129ebc52ac3c442daabb3f6d"
SRC_URI[sha256sum] = "c8cbc21bbfa1dd7d5386d48cc814fe3d35b80f60299cdde9279046f399c3b0d8"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"
