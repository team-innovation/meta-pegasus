SUMMARY = "cryptography is a package which provides cryptographic recipes and primitives to Python developers."
HOMEPAGE = "https://github.com/pyca/cryptography"
SECTION = "devel/python"

LICENSE = "Apache-2.0 | BSD-3-Clause"
LIC_FILES_CHKSUM = "\
    file://LICENSE.APACHE;md5=4e168cce331e5c827d4c2b68a6200e1b \
    file://LICENSE.BSD;md5=5ae30ba4123bc4f2fa49aa0b0dce887b \
"

SRC_URI[sha256sum] = "c63454aa261a0cf0c5b4718349629793e9e634993538db841165b3df74f37ec0"

DEPENDS += "\
    rust-native \
    cargo-native \
    openssl \
    openssl-native \
"
RDEPENDS.${PN} += "\
    ${PYTHON_PN}-setuptools-rust \
    ${PYTHON_PN}-cffi \
    ${PYTHON_PN}-pybind11 \
"

inherit python_setuptools3_rust pypi