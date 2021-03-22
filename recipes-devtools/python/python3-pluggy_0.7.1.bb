DESCRIPTION = "Python pluggy"
HOMEPAGE = "https://pypi.org/project/pluggy"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=338dad807ed9337bfaeb9979c3bfe20f"

DEPENDS += "python3-setuptools-scm-native"

PR = "r2"
SRCNAME = "pluggy-${PV}"

SRC_URI = "https://files.pythonhosted.org/packages/a1/83/ef7d976c12d67a5c7a5bc2a47f0501c926cabae9d9fcfdc26d72abc9ba15/pluggy-0.7.1.tar.gz"

S = "${WORKDIR}/${SRCNAME}"

SRC_URI[md5sum] = "cd5cc1003143f86dd6e2a865a20f8837"
SRC_URI[sha256sum] = "95eb8364a4708392bae89035f45341871286a333f749c3141c20573d2b3876e1"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir

do_configure_prepend() {
	sed -i "/setuptools-scm/d" ${S}/setup.py
}
