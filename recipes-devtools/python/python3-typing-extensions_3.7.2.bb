DESCRIPTION = "Python Typing Extensions"
HOMEPAGE = "https://pypi.org/project/typing-extensions/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=64fc2b30b67d0a8423c250e0386ed72f"

PYPI_PACKAGE = "typing_extensions"

inherit pypi setuptools3 

SRC_URI[md5sum] = "33815f263f3e00ec63b9d9d26eea95de"
SRC_URI[sha256sum] = "fb2cd053238d33a8ec939190f30cfd736c00653a85a2919415cecf7dc3d9da71"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
