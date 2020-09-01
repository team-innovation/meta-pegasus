SUMMARY = "Python xmltodict"
HOMEPAGE = "python3-xmltodict_0.12.0.bb"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=01441d50dc74476db58a41ac10cb9fa2"

SRC_URI[md5sum] = "ddb2bd078cef4f7e3021a578034ad941"
SRC_URI[sha256sum] = "50d8c638ed7ecb88d90561beedbf720c9b4e851a9fa6c47ebd64e99d166d8a21"

PYPI_PACKAGE = "xmltodict"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
