SUMMARY = "Python toolz"
HOMEPAGE = "https://pypi.python.org/pypi/toolz"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=ca09cab178326d18433aae982d1edf5d"

PR = "r1"

SRC_URI[md5sum] = "6fd07249389dd0b3bfe71d4282314328"
SRC_URI[sha256sum] = "929f0a7ea7f61c178bd951bdae93920515d3fbdbafc8e6caf82d752b9b3b31c9"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit setuptools3 pypi
