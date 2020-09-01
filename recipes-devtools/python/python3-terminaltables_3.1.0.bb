DESCRIPTION = "Generate simple tables in terminals from a nested list of strings."
HOMEPAGE = "python3-terminaltables_3.1.0.bb"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://README.rst;md5=71891cdcd37ada308a75a0c0efd60216"

SRC_URI[md5sum] = "863797674d8f75d22e16e6c1fdcbeb41"
SRC_URI[sha256sum] = "f3eb0eb92e3833972ac36796293ca0906e998dc3be91fbe1f8615b331b853b81"

PYPI_PACKAGE = "terminaltables"
inherit pypi setuptools3

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
