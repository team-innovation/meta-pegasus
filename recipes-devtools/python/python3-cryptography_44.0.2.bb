SUMMARY = "cryptography is a package which provides cryptographic recipes and primitives to Python developers."
HOMEPAGE = "https://github.com/pyca/cryptography"
SECTION = "devel/python"

LICENSE = "Apache-2.0 | BSD-3-Clause"
LIC_FILES_CHKSUM = "\
    file://LICENSE.APACHE;md5=8c3617db4fb6fae01f1d253ab91511e4 \
    file://LICENSE.BSD;md5= \
"

SRC_URI[sha256sum] = "c63454aa261a0cf0c5b4718349629793e9e634993538db841165b3df74f37ec0"

inherit setuptools3-base pypi