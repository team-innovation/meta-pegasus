DESCRIPTION = "Python simple sound library."
HOMEPAGE = "https://github.com/hamiltron/py-simple-audio"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=8d6984c9972c9cecbca9f1db663fc5bd"

DEPENDS += "alsa-lib ${PYTHON_PN}-pyalsaaudio"

inherit pypi setuptools3

SRC_URI[md5sum] = "8bd62157c286824e289e1f258419b98c"
SRC_URI[sha256sum] = "691c88649243544db717e7edf6a9831df112104e1aefb5f6038a5d071e8cf41d"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

