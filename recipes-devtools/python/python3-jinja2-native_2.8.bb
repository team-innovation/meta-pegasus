DESCRIPTION = "Python Jinja2"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=20c831f91dd3bd486020f672ba2be386"
PR = "ml1"

DEPENDS += "python3-native"

RDEPENDS_${PN} = "python3-core"

SRC_URI = "http://pypi.python.org/packages/source/J/Jinja2/Jinja2-${PV}.tar.gz \
file://fixup_setuptool_to_distutils.patch \
file://fixup_decimal_dependency.patch \
"

S = "${WORKDIR}/Jinja2-${PV}"

inherit native distutils3

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

DEPENDS_${PN} = "\
  python3-distutils \
"
SRC_URI[md5sum] = "edb51693fe22c53cee5403775c71a99e"
SRC_URI[sha256sum] = "bc1ff2ff88dbfacefde4ddde471d1417d3b304e8df103a7a9437d47269201bf4"

