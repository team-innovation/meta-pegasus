SUMMARY = "Fast implementation of asyncio event loop on top of libuv"
HOMEPAGE = "https://github.com/MagicStack/uvloop"
SECTION = "devel/python"

LICENSE = "Apache-2.0 & MIT"
LIC_FILES_CHKSUM = "\
    file://LICENSE-APACHE;md5=bb92739ddad0a2811957bd98bdb90474 \
    file://LICENSE-MIT;md5=489c8bc34154e4b59f5c58e664f7d70f \
"

SRC_URI[sha256sum] = "3bf12b0fda68447806a7ad847bfa591613177275d35b6724b1ee573faa3704e3"

DEPENDS += "\
    python3-cython-native \
    python3-pkgconfig-native \
    pkgconfig-native \
"

inherit python_setuptools_build_meta pypi

do_compile:prepend() {
	export LIBUV_CONFIGURE_HOST=${HOST_SYS}
}
