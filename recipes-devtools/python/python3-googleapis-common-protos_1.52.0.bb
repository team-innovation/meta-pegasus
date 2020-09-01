SUMMARY = "googleapis-common-protos"
DESCRIPTION = "googleapis-common-protos contains the python classes generated from the common protos"
HOMEPAGE = "https://github.com/googleapis/python-api-common-protos/"
SUMMARY = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=f209364d1186b9648abee7a950124ff0"

SRC_URI[md5sum] = "250011bead17cc2cbb8ad24cd3c2bd44"
SRC_URI[sha256sum] = "560716c807117394da12cecb0a54da5a451b5cf9866f1d37e9a5e2329a665351"

PYPI_PACKAGE = "googleapis-common-protos"
inherit setuptools3 pypi

RDEPENDS_${PN} = "${PYTHON_PN}-protobuf ${PYTHON_PN}-six"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
