DESCRIPTION = "Python cov"
HOMEPAGE = "https://pypi.org/project/pytest"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=cbc4e25353c748c817db2daffe605e43"

PR = "r1"
SRCNAME = "pytest-cov-${PV}"

SRC_URI = "https://files.pythonhosted.org/packages/d9/e2/58f90a316fbd94dd50bf5c826a23f3f5d079fb3cc448c1e9f0e3c33a3d2a/pytest-cov-2.6.0.tar.gz"

SRC_URI[md5sum] = "7c8c1bf2a5c355a4c3ad9aafe4a1894d"
SRC_URI[sha256sum] = "e360f048b7dae3f2f2a9a4d067b2dd6b6a015d384d1577c994a43f3f7cbad762"

S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
