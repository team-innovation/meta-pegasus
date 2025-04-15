SUMMARY = "BRisa is a UPnP framework."
HOMEPAGE = "https://github.com/aleixq/python3-brisa"
SECTION = "devel/python"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=de9199ce566e24392604d7bb073de4d2"

PYPI_PACKAGE = "python3-brisa"

SRC_URI = "https://files.pythonhosted.org/packages/1b/d7/de51a2bc561ee7f39586808bddd84076288d5eb4f8e715cf80e4a0acdad8/python3-brisa-${PV}.tar.gz"
SRC_URI[sha256sum] = "b90d1ddffa1b2f0a6e000958c498d80853b82bc8fd55b9d2398f69df4a8f455a"
S = "${WORKDIR}/brisa-${PV}"

RDEPENDS:${PN} += "\
  ${PYTHON_PN}-requests \
  ${PYTHON_PN}-cherrypy \
"

inherit setuptools3 pypi