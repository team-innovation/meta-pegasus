DESCRIPTION = "Python Atomic file writes."
HOMEPAGE = "https://pypi.org/project/atomicwrites"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=91cc36cfafeefb7863673bcfcb1d4da4"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit setuptools3 pypi 


SRC_URI[md5sum] = "9b64377c3f93e9877adc4460e9984f2b"
SRC_URI[sha256sum] = "ec9ae8adaae229e4f8446952d204a3e4b5fdd2d099f9be3aaf556120135fb3ee"

