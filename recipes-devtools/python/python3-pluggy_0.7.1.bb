SUMMARY = "Plugin and hook calling mechanisms for python"
HOMEPAGE = "https://github.com/pytest-dev/pluggy"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=338dad807ed9337bfaeb9979c3bfe20f"

DEPENDS += "python3-setuptools-scm-native"

SRC_URI += "file://pluggy_version_fix.patch"

SRC_URI[md5sum] = "cd5cc1003143f86dd6e2a865a20f8837"
SRC_URI[sha256sum] = "95eb8364a4708392bae89035f45341871286a333f749c3141c20573d2b3876e1"

inherit pypi setuptools3

BBCLASSEXTEND = "native nativesdk"

