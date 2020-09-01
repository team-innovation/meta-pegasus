DESCRIPTION = "cChardet is high speed universal character encoding detector."
SUMMARY = "High speed universal character encoding detector."
HOMEPAGE = "https://github.com/PyYoshi/cchardet/"
SECTION = "devel/python"
LICENSE = "MPL-1.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=6ecda54f6f525388d71d6b3cd92f7474"

SRC_URI[md5sum] = "ee884e2c7762e56e8a0600aa34ad4fa3"
SRC_URI[sha256sum] = "b76afb2059ad69eab576949980a17413c1e9e5a5624abf9e43542d8853f146b3"

PYPI_PACAKGE = "cchardet"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
