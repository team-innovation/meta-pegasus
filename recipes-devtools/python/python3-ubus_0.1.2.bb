SUMMARY = "Python bindings for libubus."
HOMEPAGE = "https://github.com/rytilahti/python-ubus"
SECTION = "devel/python"

LICENSE = "LGPL-2.0-or-later"
# The actual license files live in the upstream libgpiod from which the pypi
# package is spun out.
LIC_FILES_CHKSUM = "file://setup.py;md5=fa158e2571f6e84c10cdf99cbbacc1f5"

SRCREV = "58d914244ef47a7ba99b18222771ccdd0bb89b22"
SRC_URI = "git://github.com/rytilahti/python-ubus.git;branch=master;protocol=https"
S = "${WORKDIR}/git"

inherit setuptools3