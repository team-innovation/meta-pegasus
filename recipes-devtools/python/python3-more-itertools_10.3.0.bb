SUMMARY = "More routines for operating on iterables, beyond itertools"
HOMEPAGE = "https://github.com/more-itertools/more-itertools"
SECTION = "devel/python"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3396ea30f9d21389d7857719816f83b5"

SRC_URI[sha256sum] = "e5d93ef411224fbcef366a6e8ddc4c5781bc6359d43412a65dd5964e46111463"

DEPENDS += "python3-setuptools-scm-native"

inherit python_setuptools_build_meta pypi
