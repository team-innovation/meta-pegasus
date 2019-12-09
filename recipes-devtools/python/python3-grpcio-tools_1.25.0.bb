DESCRIPTION = "Google gRPC tools"
HOMEPAGE = "http://www.grpc.io/"
SECTION = "devel/python"

DEPENDS = "python3-grpcio"
RDEPENDS_${PN} = "python3-grpcio"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://MANIFEST.in;md5=4e1da04207560d860fc7e2eeb805727f"

inherit pypi setuptools3

SRC_URI += "file://compiler.patch"

SRC_URI[md5sum] = "00813e18ece15b46f2df104e377343ee"
SRC_URI[sha256sum] = "988014c714ca654b3b7ca9f4dabfe487b00e023bfdd9eaf1bb0fed82bf8c4255"

# For usage in other recipies when compiling protobuf files (e.g. by grpcio-tools)
BBCLASSEXTEND = "native"
