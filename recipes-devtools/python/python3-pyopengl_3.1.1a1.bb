SUMMARY = "Standard OpenGL bindings for Python"
SECTION = "devel/python"
PRIORITY = "optional"
DEPENDS = "python3-numpy"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://license.txt;md5=aca224cd79879c4a6674633d31043fa8"
PYPI_PACKAGE = "PyOpenGL"

PR = "r1"

SRC_URI += "\
  file://platform_init_py.patch \
"

inherit setuptools3 pypi

SRC_URI[md5sum] = "77ee6044ceb2cf952aca89a9b2d3d585"
SRC_URI[sha256sum] = "c96d909b359abe3271b746bacf7e6ba52935141e2406a8f90231e4e44dfa4075"

