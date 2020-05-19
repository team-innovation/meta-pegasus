DESCRIPTION = "Simple DNS resolver for asyncio"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "MIT"
SRCNAME = "aiodns"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a565d8b5d06b9620968a135a2657b093"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit setuptools3 pypi

SRC_URI[md5sum] = "b0fb02275cafcbe5ad531c72ba950615"
SRC_URI[sha256sum] = "d67e14b32176bcf3ff79b5d47c466011ce4adeadfa264f7949da1377332a0449"
