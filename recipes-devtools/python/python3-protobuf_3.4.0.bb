DESCRIPTION = "Protocol Buffers"
HOMEPAGE = "https://developers.google.com/protocol-buffers/"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

PR = "r1"

RDEPENDS_${PN} = "python3-six"

SRC_URI = "https://files.pythonhosted.org/packages/source/p/protobuf/protobuf-${PV}.tar.gz"

SRC_URI[md5sum] = "bfc0c61c156a995e909521697e755780"
SRC_URI[sha256sum] = "ef02609ef445987976a3a26bff77119c518e0915c96661c3a3b17856d0ef6374"

SRCNAME ="protobuf-${PV}"
S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
