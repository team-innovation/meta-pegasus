DESCRIPTION = "Python sentry"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0c79f8d3c91fc847350efd28bfe0a341"

SRC_URI[md5sum] = "33bfdd7541c17726c19925cec9f8fd07"
SRC_URI[sha256sum] = "78bb79adfe991a770ce2179826f5f216e086bbc4bd4585c543f377180cae3654"

inherit pypi setuptools3 

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"


