SUMMARY = "TCP port monitoring and discovery"
HOMEPAGE = "https://github.com/jaraco/portend"
SECTION = "devel/python"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"

SRC_URI[sha256sum] = "5250a352c19c959d767cac878b829d93e5dc7625a5143399a2a00dc6628ffb72"

DEPENDS += "python3-setuptools-scm-native"

inherit python_setuptools_build_meta pypi