DESCRIPTION = "Python pytest"
HOMEPAGE = "https://pypi.org/project/pytest"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c39b24965f4aef64222cb35de9d47cc4"

DEPENDS += "python3-setuptools-scm-native"

PR = "r2"
SRCNAME = "pytest-${PV}"

SRC_URI = "https://files.pythonhosted.org/packages/5f/d2/7f77f406ac505abda02ab4afb50d06ebf304f6ea42fca34f8f37529106b2/pytest-3.8.2.tar.gz"

SRC_URI[md5sum] = "8e7d324528a63c2dab64a10ae028e0f2"
SRC_URI[sha256sum] = "9332147e9af2dcf46cd7ceb14d5acadb6564744ddff1fe8c17f0ce60ece7d9a2"

S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir

do_configure_prepend() {
	sed -i "/setuptools-scm/d" ${S}/setup.py
}
