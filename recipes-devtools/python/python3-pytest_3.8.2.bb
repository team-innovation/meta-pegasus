DESCRIPTION = "Python pytest"
HOMEPAGE = "https://pypi.org/project/pytest"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c39b24965f4aef64222cb35de9d47cc4"


SRC_URI[md5sum] = "8e7d324528a63c2dab64a10ae028e0f2"
SRC_URI[sha256sum] = "9332147e9af2dcf46cd7ceb14d5acadb6564744ddff1fe8c17f0ce60ece7d9a2"

inherit pypi setuptools3 update-alternatives

RDEPENDS_${PN}_class-target += " \
    python3-attrs \
    python3-debugger \
    python3-doctest \
    python3-json \
    python3-pluggy \
    python3-py \
    python3-setuptools \
    python3-six \
"
FILESEXTRAPATHS_prepend := "${THISDIR}/python-pytest:"

ALTERNATIVE_${PN} += "py.test pytest"

NATIVE_LINK_NAME[pytest] = "${bindir}/pytest"
ALTERNATIVE_TARGET[pytest] = "${bindir}/pytest"

ALTERNATIVE_LINK_NAME[py.test] = "${bindir}/py.test"
ALTERNATIVE_TARGET[py.test] = "${bindir}/py.test"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"
