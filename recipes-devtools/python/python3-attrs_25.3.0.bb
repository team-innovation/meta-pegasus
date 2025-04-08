SUMMARY = "Classes Without Boilerplate"
HOMEPAGE = "https://github.com/python-attrs/attrs"
SECTION = "devel/python"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5e55731824cf9205cfabeab9a0600887"

SRC_URI[sha256sum] = "75d7cefc7fb576747b2c81b4442d4d4a1ce0900973527c011d1030fd3bf4af1b"

DEPENDS = "\
    {PYTHON_PN}-hatchling-native \
    {PYTHON_PN}-hatch-vcs-native \
    {PYTHON_PN}-hatch-fancy-pypi-readme-native \
"

inherit setuptools3-base pypi