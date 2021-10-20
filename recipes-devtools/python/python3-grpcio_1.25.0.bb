DESCRIPTION = "Google gRPC"
HOMEPAGE = "http://www.grpc.io/"
SECTION = "devel/python"

DEPENDS = "python3-protobuf python3-cython"

RDEPENDS_${PN} = "python3-protobuf \
                  python3-setuptools \
                  python3-six \
"


LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

inherit pypi setuptools3

SRC_URI_append = " file://disable-grpc-forking.patch file://compiler.patch"

SRC_URI[md5sum] = "14c23bbb1db72f6b8fe262b549203eb3"
SRC_URI[sha256sum] = "c948c034d8997526011960db54f512756fb0b4be1b81140a15b4ef094c6594a4"

# For usage in other recipes when compiling protobuf files (e.g. by grpcio-tools)
BBCLASSEXTEND = "native nativesdk"

do_compile_prepend() {
	
}
