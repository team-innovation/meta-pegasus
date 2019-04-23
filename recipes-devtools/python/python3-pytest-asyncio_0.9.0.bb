DESCRIPTION = "Pytest asyncio"
HOMEPAGE = "https://pypi.org/project/pytest"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.rst;md5=a5a6430d01c31284ee7f42ac2ef8d9f9"

PR = "r1"
SRCNAME = "pytest-asyncio-${PV}"

SRC_URI = "https://files.pythonhosted.org/packages/22/fd/6be59ed6eb49798d248730b8c2589e4f862a39b49775a1415972b31a3362/pytest-asyncio-0.9.0.tar.gz"

SRC_URI[md5sum] = "b28f308fcde244a53e5d82c807423de3"
SRC_URI[sha256sum] = "fbd92c067c16111174a1286bfb253660f1e564e5146b39eeed1133315cf2c2cf"

S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
