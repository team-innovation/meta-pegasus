SUMMARY = "Fundamental package for array computing in Python"
HOMEPAGE = "https://github.com/numpy/numpy"
SECTION = "devel/python"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=3fd3c9b0f5e17c781b49b05450d4cb2a"

SRC_URI[sha256sum] = "ab093e181c65f7fa415fa3c94320be328ad7c2e35b03d4a7fddf437e20fc989f"

DEPENDS += "python3-cython-native"
RDEPENDS:${PN} += "\
    python3-core \
    python3-math \
    python3-numbers \
"

inherit python_setuptools_build_meta pypi