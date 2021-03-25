DESCRIPTION = "Python py"
HOMEPAGE = "https://pypi.org/project/py"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a6bb0320b04a0a503f12f69fea479de9"

PR = "r2"
SRCNAME = "py-${PV}"

SRC_URI = "https://files.pythonhosted.org/packages/4f/38/5f427d1eedae73063ce4da680d2bae72014995f9fdeaa57809df61c968cd/py-1.6.0.tar.gz"
SRC_URI += "file://py_version_fix.patch"

S = "${WORKDIR}/${SRCNAME}"

SRC_URI[md5sum] = "5ccd0cd5373c55171cf9fd61b9f19a1b"
SRC_URI[sha256sum] = "06a30435d058473046be836d3fc4f27167fd84c45b99704f2fb5509ef61f9af1"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
