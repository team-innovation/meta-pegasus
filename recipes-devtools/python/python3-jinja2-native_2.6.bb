DESCRIPTION = "Python Jinja2"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=20c831f91dd3bd486020f672ba2be386"
PR = "ml1"

DEPENDS += "python3-native"

RDEPENDS_${PN} = "python3-core"

SRC_URI = "http://pypi.python.org/packages/source/J/Jinja2/Jinja2-${PV}.tar.gz \
file://fixup_setuptool_to_distutils.patch"

S = "${WORKDIR}/Jinja2-${PV}"

inherit native distutils3

do_compile_append() {
    sed -i 's|xrange|range|g' ${S}/build/lib/jinja2/*.py
}


do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

DEPENDS_${PN} = "\
  python3-distutils \
"
SRC_URI[md5sum] = "1c49a8825c993bfdcf55bb36897d28a2"
SRC_URI[sha256sum] = "a85e185375db75c109f99ecde3d55415f035e7068a82252cf4f16c667ac38f49"

