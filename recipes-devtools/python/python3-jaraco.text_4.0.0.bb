SUMMARY = "Routines for dealing with text"
HOMEPAGE = "https://github.com/jaraco/jaraco.text"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"

SRC_URI[sha256sum] = "5b71fecea69ab6f939d4c906c04fee1eda76500d1641117df6ec45b865f10db0"

inherit pypi python_setuptools_build_meta

DEPENDS += "python3-setuptools-scm-native"

PYPI_PACKAGE = "jaraco_text"
UPSTREAM_CHECK_PYPI_PACKAGE = "${PYPI_PACKAGE}"

BBCLASSEXTEND = "native nativesdk"
