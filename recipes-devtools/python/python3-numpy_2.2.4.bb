SUMMARY = "Fundamental package for array computing in Python"
HOMEPAGE = "https://github.com/numpy/numpy"
SECTION = "devel/python"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=1de863c37a83e71b1e97b64d036ea78b"

SRC_URI[sha256sum] = "9ba03692a45d3eef66559efe1d1096c4b9b75c0986b5dff5530c378fb8331d4f"

DEPENDS += " \
    python3-cython-native \
    python3-wheel-native \
    python3-meson-python-native \
    meson-native \
    ninja-native \
"

RDEPENDS:${PN} += "\
    python3-core \
    python3-math \
    python3-numbers \
"

inherit python_pep517 pypi