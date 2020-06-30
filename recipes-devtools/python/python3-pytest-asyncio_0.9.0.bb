DESCRIPTION = "Pytest asyncio"
HOMEPAGE = "https://pypi.org/project/pytest"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.rst;md5=a5a6430d01c31284ee7f42ac2ef8d9f9"

inherit pypi setuptools3

SRC_URI[md5sum] = "b28f308fcde244a53e5d82c807423de3"
SRC_URI[sha256sum] = "fbd92c067c16111174a1286bfb253660f1e564e5146b39eeed1133315cf2c2cf"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

