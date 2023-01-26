SUMMARY = "Python Library called jacobs-json-schema"
HOMEPAGE = "https://github.com/pearmaster/jacobs-json-schema"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4e408caf27642384f3ed2e8443a88974"

SRC_URI[md5sum] = "7f2048cddf590a31e180427c7f50530f"

PYPI_PACKAGE = "jacobs-json-schema"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
