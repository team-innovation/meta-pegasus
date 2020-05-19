DESCRIPTION = "multidict"
HOMEPAGE = "https://pypi.org/project/multidict/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e74c98abe0de8f798ca609137f9cef4a"

inherit pypi setuptools3

SRC_URI[md5sum] = "20580d5a39570c6ef81664999514ce65"
SRC_URI[sha256sum] = "5ba766433c30d703f6b2c17eb0b6826c6f898e5f58d89373e235f07764952314"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

