SUMMARY = "Python bindings for libubus."
HOMEPAGE = "https://pypi.org/project/ubus/"
SECTION = "devel/python"

LICENSE = "LGPL-2.0-or-later"
# The actual license files live in the upstream libgpiod from which the pypi
# package is spun out.
LIC_FILES_CHKSUM = "file://LICENSE;md5=4fbd65380cdd255951079008b364516c"

SRC_URI[sha256sum] = "4dc4ef0fbcc8abb7a2354691475a58ff3eb015f1bab3150750729f7f657dd440"

inherit setuptools3 python_pep517 pypi