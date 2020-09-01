SUMMARY = "Python library for signing HTTP signatures with ed5519." 
DESCRIPTION = "Python library for signing HTTP signatures. The Na stands for sodium, because this version supports ed25519 signing from the libsodium library"
HOMEPAGE = "https://github.com/ahknight/httpsig"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=10192cdd65101da73ef82fa84f2f7b2f"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI += "\ 
    file://0002-Added-ed25519-signing-via-pysodium-as-optional-depen.patch \
"

SRC_URI[md5sum] = "eb5ec4c73fd84ae0a1374034d51d3256"
SRC_URI[sha256sum] = "71d6d50246129c4f7cfec20f5e57e351d2b8492d631cc2aa967914acf91f6ce6"

PYPI_PACKAGE = "httpsig"
inherit setuptools3 pypi

RDEPENDS_${PN} = "${PYTHON_PN}-pysodium"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
