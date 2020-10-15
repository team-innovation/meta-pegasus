SUMMARY = "Google gRPC tools"
DESCRIPTION = "Package for gRPC Python tools"
HOMEPAGE = "http://www.grpc.io/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://MANIFEST.in;md5=4e1da04207560d860fc7e2eeb805727f"

DEPENDS = "${PYTHON_PN}-grpcio"

SRC_URI_append = " file://compiler.patch"

SRC_URI[md5sum] = "bf292a112c0a0598d70e31e93d9109e7"
SRC_URI[sha256sum] = "3b08cbd3f4d5b60e3bff8f859e6e03db739967a684268164abc940415e23ca51"

inherit pypi setuptools3

RDEPENDS_${PN} = "${PYTHON_PN}-grpcio"

# For usage in other recipies when compiling protobuf files (e.g. by grpcio-tools)
BBCLASSEXTEND = "native nativesdk"
