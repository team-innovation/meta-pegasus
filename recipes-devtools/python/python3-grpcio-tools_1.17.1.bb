DESCRIPTION = "grpcio-tools"
HOMEPAGE = "https://pypi.org/project/grpcio-tools/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://MANIFEST.in;md5=4e1da04207560d860fc7e2eeb805727f"

DEPENDS += "python3-cython python3-grpcio"
RDEPENDS_${PN} = "python3-protobuf python3-six"

PR = "r0"

SRC_URI[md5sum] = "f51f965ca452cc67388baaf9c4f6a38a"
SRC_URI[sha256sum] = "cbf98c7623366170c2049515f0a5bbe82af7d09f987a227d5760763e5e3646b2"

S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit setuptools3 pypi
