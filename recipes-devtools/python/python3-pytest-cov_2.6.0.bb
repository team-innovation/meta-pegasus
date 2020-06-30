DESCRIPTION = "Python cov"
HOMEPAGE = "https://pypi.org/project/pytest"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=cbc4e25353c748c817db2daffe605e43"

inherit pypi setuptools3 

SRC_URI[md5sum] = "7c8c1bf2a5c355a4c3ad9aafe4a1894d"
SRC_URI[sha256sum] = "e360f048b7dae3f2f2a9a4d067b2dd6b6a015d384d1577c994a43f3f7cbad762"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

