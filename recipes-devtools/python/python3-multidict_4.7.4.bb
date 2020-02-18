DESCRIPTION = "multidict"
HOMEPAGE = "https://pypi.org/project/multidict/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e74c98abe0de8f798ca609137f9cef4a"

DEPENDS += "python3"

PR = "r2"
SRCNAME = "multidict"

SRC_URI = "https://files.pythonhosted.org/packages/b6/22/ae21cedaa0e6d35e84e8ab57700dcf3d4609421ebe113e1aaafc468eec42/${SRCNAME}-${PV}.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "22b46f759cf2cc3ca1d2c9f82cc9bb79"
SRC_URI[sha256sum] = "d7d428488c67b09b26928950a395e41cc72bb9c3d5abfe9f0521940ee4f796d4"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

do_package_qa() {
    echo "Skipping QA ..."
}

inherit setuptools3 python3-dir
