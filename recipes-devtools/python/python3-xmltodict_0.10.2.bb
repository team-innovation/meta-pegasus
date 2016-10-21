DESCRIPTION = "Python xmltodict"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=01441d50dc74476db58a41ac10cb9fa2"
PR = "r1"
SRCNAME = "xmltodict"

SRC_URI = "https://pypi.python.org/packages/4a/5e/cd36c16c9eca47162fbbea9aa723b9ab3010f9ae9d4be5c9f6cb2bc147ab/xmltodict-0.10.2.tar.gz \
	   file://setup.py_distutil_fix.patch"

SRC_URI[md5sum] = "1275cbb2e66c354eb2b6a6bc946f3fa1"
SRC_URI[sha256sum] = "fc518ccf9adbbb917a2ddcb386be852ae6dd36935e1e8b9a3e760201abfdbf77"

S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit distutils3
