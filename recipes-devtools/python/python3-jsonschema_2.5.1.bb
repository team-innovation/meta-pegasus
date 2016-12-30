DESCRIPTION = "Python jsonschema"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://json/LICENSE;md5=9d4de43111d33570c8fe49b4cb0e01af"
PR = "r1"
SRCNAME = "jsonschema"

SRC_URI = "https://pypi.python.org/packages/58/0d/c816f5ea5adaf1293a1d81d32e4cdfdaf8496973aa5049786d7fdb14e7e7/jsonschema-2.5.1.tar.gz \
	   file://setup.py_distutil_fix.patch"

SRC_URI[md5sum] = "374e848fdb69a3ce8b7e778b47c30640"
SRC_URI[sha256sum] = "36673ac378feed3daa5956276a829699056523d7961027911f064b52255ead41"

S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit distutils3
