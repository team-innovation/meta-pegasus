SUMMARY = "Google gRPC"
DESCRIPTION = "Package for gRPC Python"
HOMEPAGE = "http://www.grpc.io/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = "${PYTHON_PN}-protobuf"

SRC_URI_append_class-target = " \
    file://0001-setup.py-Do-not-mix-C-and-C-compiler-options.patch \
    file://ppc-boringssl-support.patch \
    file://riscv64_support.patch \
    file://disable-grpc-forking.patch \
    file://compiler.patch \
"

SRC_URI_append_class-native = " file://compiler.patch"

SRC_URI[md5sum] = "810e3c4b54fa2aa3827f9aac158f1564"
SRC_URI[sha256sum] = "5043440c45c0a031f387e7f48527541c65d672005fb24cf18ef6857483557d39"

PYPI_PACKAGE = "grpcio"
inherit pypi setuptools3

RDEPENDS_${PN} = "${PYTHON_PN}-protobuf \
                  ${PYTHON_PN}-setuptools \
                  ${PYTHON_PN}-six \
"
CLEANBROKEN = "1"

# For usage in other recipes when compiling protobuf files (e.g. by grpcio-tools)
BBCLASSEXTEND = "native nativesdk"
