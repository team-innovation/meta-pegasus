DESCRIPTION = "Fast implementation of asyncio event loop on top of libuv"
HOMEPAGE = "https://pypi.org/project/uvloop/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE-MIT;md5=3bd0b2e751776b370b2151ac508af939"

inherit pypi setuptools3

DEPENDS += "python3 python3-cython"

SRC_URI[md5sum] = "3f57bccd1b37de92cb4a432415f9704e"
SRC_URI[sha256sum] = "afdf34bf507090e4c7f5108a17240982760356b8aae4edd37180ec4f94c36cbb"

do_compile_prepend() {
        export LIBUV_CONFIGURE_HOST=${HOST_SYS}
}

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"
