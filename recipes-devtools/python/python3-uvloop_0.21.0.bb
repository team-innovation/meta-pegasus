SUMMARY = "Fast implementation of asyncio event loop on top of libuv"
HOMEPAGE = "https://github.com/MagicStack/uvloop"
SECTION = "devel/python"

LICENSE = "Apache-2.0 & MIT"
LIC_FILES_CHKSUM = "\
    file://LICENSE-APACHE;md5= \
    file://LICENSE-MIT;md5= \
"

SRC_URI[sha256sum] = ""

inherit python_setuptools_build_meta pypi