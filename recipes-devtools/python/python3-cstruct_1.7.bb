SUMMARY = "C-style structs for Python"
DESCRIPTION = "Convert C struct definitions into Python classes with methods for serializing/deserializing."
HOMEPAGE = "https://github.com/andreax79/python-cstruct"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.md;md5=c82719bb922baaf3b4238bc7e88f274d"

SRC_URI[md5sum] = "0d90ebc9396b15f8703263f8e0fa40a3"
SRC_URI[sha256sum] = "42c6239336fe2d7119941ac3f9ca360e07148d715b742231e3dcc3a5ddd76492"

PYPI_PACKAGE = "cstruct"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
