DESCRIPTION = "Standard OpenGL bindings for Python"
SECTION = "devel/python"
PRIORITY = "optional"
DEPENDS = "python3-numpy"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://license.txt;md5=aca224cd79879c4a6674633d31043fa8"
SRCNAME = "PyOpenGL"

PR = "r1"

SRC_URI = "\
  https://pypi.python.org/packages/source/P/${SRCNAME}/${SRCNAME}-${PV}.tar.gz \
  file://platform_init_py.patch \
"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools3 python3-dir

SRC_URI[md5sum] = "77ee6044ceb2cf952aca89a9b2d3d585"
SRC_URI[sha256sum] = "c96d909b359abe3271b746bacf7e6ba52935141e2406a8f90231e4e44dfa4075"

pkg_postinst_${PN}() {
#!/bin/sh -e
#
# We need to run ldconfig otherwise we cannot find the 
# GL library when try to use it
if [ x"$D" = "x" ]; then
    echo "Running ldconfig"
    ldconfig
else
    exit 1
fi
}
