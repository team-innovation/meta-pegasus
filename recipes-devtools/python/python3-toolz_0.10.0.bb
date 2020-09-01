SUMMARY = "Python toolz"
HOMEPAGE = "https://github.com/pytoolz/toolz/"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=ca09cab178326d18433aae982d1edf5d"

SRC_URI[md5sum] = "3cb4317dbaff18b5a9201b69e57692a6"
SRC_URI[sha256sum] = "08fdd5ef7c96480ad11c12d472de21acd32359996f69a5259299b540feba4560"

PYPI_PACKAGE = "toolz"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
