SUMMARY = "TCP port monitoring and discovery"
HOMEPAGE = "https://github.com/jaraco/portend"
SECTION = "devel/python"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=141643e11c48898150daa83802dbc65f"

SRC_URI[sha256sum] = "5250a352c19c959d767cac878b829d93e5dc7625a5143399a2a00dc6628ffb72"

DEPENDS += "\
    ${PYTHON_PN}-setuptools-scm-native \
"

RDEPENDS:${PN} += "\
    ${PYTHON_PN}-io \
"

RDEPENDS:${PN} += "\
    ${PYTHON_PN}-tempora \
"

inherit python_setuptools_build_meta pypi

BBCLASSEXTEND = "native nativesdk"
