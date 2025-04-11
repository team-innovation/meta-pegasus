SUMMARY = "A memory profiler for Python applications"
HOMEPAGE = "https://github.com/bloomberg/memray"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5de5671aa80e3d8ebba56399d18cad14"

SRC_URI[sha256sum] = "57319f74333a58a9b3d5fba411b0bff3f2974a37e362a09a577e7c6fe1d29a36"

DEPENDS = "\
    ${PYTHON_PN}-setuptools-scm-native \
    ${PYTHON_PN}-pkgconfig-native \
    cython-native \
"

inherit python_setuptools_build_meta pypi