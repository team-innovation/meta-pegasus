DESCRIPTION = "Python sentry"
SECTION = "devel/python"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0c79f8d3c91fc847350efd28bfe0a341"
PR = "r1"
SRCNAME = "sentry-sdk"

SRC_URI = "https://files.pythonhosted.org/packages/df/fb/a7d8c99facdcb1327219085c6a84afc841194e6c1c2e919461bde797796d/sentry-sdk-0.12.3.tar.gz"

SRC_URI[md5sum] = "823000295e82fe25a99eee5e4c9dd1f4"
SRC_URI[sha256sum] = "15e51e74b924180c98bcd636cb4634945b0a99a124d50b433c3a9dc6a582e8db"

inherit pypi setuptools3 

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"


