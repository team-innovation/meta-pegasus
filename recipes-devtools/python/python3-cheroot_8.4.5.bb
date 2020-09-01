DESCRIPTION = "Python Cheroot"
SECTION = "devel/python"
HOMEPAGE = "https://cheroot.cherrypy.org/en/latest/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=53e455722e37d6acfe57bf370663edb5"

DEPENDS += "${PYTHON_PN}-setuptools-scm-git-archive-native"

SRC_URI[md5sum] = "34a500fe1a12ba80af2d088721b2930c"
SRC_URI[sha256sum] = "b6c18caf5f79cdae668c35fc8309fc88ea4a964cce9e2ca8504fab13bcf57301"

PYPI_PACKAGE = "cheroot"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
