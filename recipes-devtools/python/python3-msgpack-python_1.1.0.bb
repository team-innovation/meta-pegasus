SUMMARY = "MessagePack (de)serializer."
HOMEPAGE = "https://github.com/msgpack/msgpack-python"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5="

SRC_URI = "https://github.com/msgpack/msgpack-python/archive/refs/tags/v{PV}.tar.gz"
SRC_URI[sha256sum] = ""
S = "${WORKDIR}/msgpack-python-${PV}"

inherit setuptools3