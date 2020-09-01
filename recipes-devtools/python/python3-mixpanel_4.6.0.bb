SUMMARY = "Mixpanel client library for Python"
HOMEPAGE = "https://github.com/mixpanel/mixpanel-python"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=95c9dfdedc62b6e79bde85e3ed0f02aa"

SRC_URI[md5sum] = "d42be76c397b9fd3f8237d9629d4711d"
SRC_URI[sha256sum] = "a0a2adba595ad75fecc5e28e46f25d1c8c9bf30fbe704147c48dfab307a84c02"

PYPI_PACKAGE = "mixpanel"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
