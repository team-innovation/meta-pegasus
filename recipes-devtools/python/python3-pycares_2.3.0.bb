DESCRIPTION = "Python interface for c-ares"
HOMEPAGE = "https://pypi.org/project/pycares/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b1538fcaea82ebf2313ed648b96c69b1"

inherit pypi setuptools3 

SRC_URI[md5sum] = "74893b2b380fbb45329a406ae4b1ae89"
SRC_URI[sha256sum] = "36f4c03df57c41a87eb3d642201684eb5a8bc194f4bafaa9f60ee6dc0aef8e40"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

