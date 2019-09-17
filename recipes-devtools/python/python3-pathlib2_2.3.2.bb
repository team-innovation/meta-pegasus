DESCRIPTION = "Python pathlib"
HOMEPAGE = "https://pypi.org/project/pathlib2"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=042856c23a3e903b33bf361ea1cbe29a"

PR = "r1"
SRCNAME = "pathlib2-${PV}"

SRC_URI = "https://files.pythonhosted.org/packages/db/a8/7d6439c1aec525ed70810abee5b7d7f3aa35347f59bc28343e8f62019aa2/pathlib2-2.3.2.tar.gz"

S = "${WORKDIR}/${SRCNAME}"

SRC_URI[md5sum] = "fd76fb5d0baa798bfe12fb7965da97f8"
SRC_URI[sha256sum] = "8eb170f8d0d61825e09a95b38be068299ddeda82f35e96c3301a8a5e7604cb83"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
