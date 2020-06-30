DESCRIPTION = "Python py"
HOMEPAGE = "https://pypi.org/project/py"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a6bb0320b04a0a503f12f69fea479de9"

inherit pypi setuptools3 

SRC_URI[md5sum] = "5ccd0cd5373c55171cf9fd61b9f19a1b"
SRC_URI[sha256sum] = "06a30435d058473046be836d3fc4f27167fd84c45b99704f2fb5509ef61f9af1"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

