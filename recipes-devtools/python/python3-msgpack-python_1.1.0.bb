SUMMARY = "MessagePack (de)serializer."
HOMEPAGE = "https://github.com/msgpack/msgpack-python"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=cd9523181d9d4fbf7ffca52eaa2a5751"

SRC_URI = "https://files.pythonhosted.org/packages/source/m/msgpack/msgpack-${PV}.tar.gz"
SRC_URI[sha256sum] = "dd432ccc2c72b914e4cb77afce64aab761c1137cc698be3984eee260bcb2896e"
S = "${WORKDIR}/msgpack-${PV}"

inherit python_setuptools_build_meta