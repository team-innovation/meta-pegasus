SUMMARY = "Collections like those found in stdlib"
HOMEPAGE = "https://github.com/jaraco/jaraco.collections"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"

SRC_URI[sha256sum] = "0e4829409d39ad18a40aa6754fee2767f4d9730c4ba66dc9df89f1d2756994c2"

inherit pypi python_setuptools_build_meta

DEPENDS += "python3-setuptools-scm-native"

PYPI_PACKAGE = "jaraco_collections"
UPSTREAM_CHECK_PYPI_PACKAGE = "${PYPI_PACKAGE}"

RDEPENDS:${PN} = "python3-jaraco.text"

BBCLASSEXTEND = "native nativesdk"
