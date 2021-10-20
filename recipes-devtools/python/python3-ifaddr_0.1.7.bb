DESCRIPTION = "Python ifaddr"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=8debe8d42320ec0ff24665319b625a5e"
PR = "r2"
SRCNAME = "ifaddr"

DEPENDS += "python3"
RDEPENDS_${PN} = "\
"
SRC_URI = "https://files.pythonhosted.org/packages/3d/fc/4ce147e3997cd0ea470ad27112087545cf83bf85015ddb3054673cb471bb/${SRCNAME}-${PV}.tar.gz"

SRC_URI[md5sum] = "97c4eb7505643b5f1fe17733cb42abd9"

S = "${WORKDIR}/${SRCNAME}-${PV}"

do_compile_prepend() {
    rm ${S}/README.rst
}

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"


inherit setuptools3 python3-dir
