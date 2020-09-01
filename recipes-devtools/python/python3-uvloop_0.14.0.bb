DESCRIPTION = "Fast implementation of asyncio event loop on top of libuv"
HOMEPAGE = "https://github.com/MagicStack/uvloop"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE-MIT;md5=3bd0b2e751776b370b2151ac508af939"

SRC_URI[md5sum] = "a2f82abb676756f11f544c6b51caf171"
SRC_URI[sha256sum] = "123ac9c0c7dd71464f58f1b4ee0bbd81285d96cdda8bc3519281b8973e3a461e"

PYPI_PACKAGE = "uvloop"
inherit pypi setuptools3

do_compile_prepend() {
	export LIBUV_CONFIGURE_HOST=${HOST_SYS}
}

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
