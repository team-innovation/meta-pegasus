SUMMARY = "Fast implementation of asyncio event loop on top of libuv"
HOMEPAGE = "https://github.com/MagicStack/uvloop"
SECTION = "devel/python"

LICENSE = "Apache-2.0 & MIT"
LIC_FILES_CHKSUM = "\
    file://LICENSE-APACHE;md5= \
    file://LICENSE-MIT;md5= \
"

SRC_URI[sha256sum] = "3bf12b0fda68447806a7ad847bfa591613177275d35b6724b1ee573faa3704e3"

inherit python_setuptools_build_meta pypi