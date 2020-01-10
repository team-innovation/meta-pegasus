DESCRIPTION = "pydevd for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=30b3836521b3d65bef598bbc358a3afa"
SRCNAME = "pydevd"
PR = "ml"

DEPENDS += "python3"
SRC_URI = "https://files.pythonhosted.org/packages/fe/47/77aaa3552aa638cb01c397fe0938b42ff995f2e1bdacd1041fdea7a2fedb/pydevd-1.4.0.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir

SRC_URI[md5sum] = "8117d9b1245a50532b867e56f8b73e51"
SRC_URI[sha256sum] = "313b65bd140435a65f39c18cd922751aae27fc6ea6c0c8179a887ad8e2ae9083"


do_compile_append() {
    rm ${B}/pydevd_attach_to_process/attach_linux_amd64.so
    rm ${B}/pydevd_attach_to_process/attach_linux_x86.so
}

FILES_${PN} += "/usr/share/pydevd_attach_to_process/*"
