SUMMARY = "pydevd for Python"
HOMEPAGE = "https://github.com/fabioz/PyDev.Debugger/"
SECTION = "devel/python"
LICENSE = "EPL-1.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=30b3836521b3d65bef598bbc358a3afa"

SRC_URI[md5sum] = "11c7ca39a895a630cd7fe5701d7bc2fd"
SRC_URI[sha256sum] = "61a3300d011cfb9e88f420d24585c64479ce832c0bf23bb979b3e074445dca11"

PYPI_PACKAGE = "pydevd"
inherit pypi setuptools3

do_install_append() {
    rm -f ${D}${datadir}/pydevd_attach_to_process/attach_linux_amd64.so
    rm -f ${D}${datadir}/pydevd_attach_to_process/attach_linux_x86.so
}

FILES_${PN} += "${datadir}/*"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
