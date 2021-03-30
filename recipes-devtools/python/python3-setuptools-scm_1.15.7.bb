SUMMARY = "the blessed package to manage your versions by scm tags"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;beginline=8;endline=8;md5=8227180126797a0148f94f483f3e1489"

SRC_URI[md5sum] = "1b55f9cedde4a33ab5d286c5f30e86db"
SRC_URI[sha256sum] = "e163e8a12d2121f77575773cfc2b5988275dc1f1d2541fdf780127c29dbbea9c"

inherit setuptools3

SRC_URI = "https://files.pythonhosted.org/packages/source/s/setuptools-scm/setuptools_scm-${PV}.tar.gz"

S = "${WORKDIR}/setuptools_scm-${PV}"

RDEPENDS_${PN}_class-target = "${PYTHON_PN}-py ${PYTHON_PN}-setuptools ${PYTHON_PN}-debugger ${PYTHON_PN}-json"
RDEPENDS_${PN}_class-native = "${PYTHON_PN}-setuptools-native"

BBCLASSEXTEND = "native"
