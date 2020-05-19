DESCRIPTION = "pip for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=593c6cd9d639307226978cbcae61ad4b"
SRCNAME = "pip"
PR = "ml"

DEPENDS += "python3 python3-setuptools-native"
SRC_URI = "https://files.pythonhosted.org/packages/45/ae/8a0ad77defb7cc903f09e551d88b443304a9bd6e6f124e75c0fbbf6de8f7/pip-18.1.tar.gz"

SRC_URI[md5sum] = "75cad449ad62c88b22de317a26781714"
SRC_URI[sha256sum] = "c0a292bd977ef590379a3f05d7b7f65135487b67470f6281289a94e015650ea1"

inherit pypi distutils3

DISTUTILS_INSTALL_ARGS += "--install-lib=${D}${PYTHON_SITEPACKAGES_DIR}"

do_install_prepend() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
}

# Use setuptools site.py instead, avoid shared state issue
do_install_append() {
    rm ${D}${PYTHON_SITEPACKAGES_DIR}/site.py
    rm ${D}${PYTHON_SITEPACKAGES_DIR}/__pycache__/site.cpython-*.pyc

    # Install as pip3 and leave pip2 as default
    rm ${D}/${bindir}/pip

    # Installed eggs need to be passed directly to the interpreter via a pth file
    echo "./${PYPI_PACKAGE}-${PV}-py${PYTHON_BASEVERSION}.egg" > ${D}${PYTHON_SITEPACKAGES_DIR}/${PYPI_PACKAGE}-${PV}.pth

    # Make sure we use /usr/bin/env python3
    for PYTHSCRIPT in `grep -rIl ${bindir} ${D}${bindir}/pip3*`; do
        sed -i -e '1s|^#!.*|#!/usr/bin/env python3|' $PYTHSCRIPT
    done
}

RDEPENDS_${PN} = "\
  python3-compile \
  python3-io \
  python3-html \
  python3-json \
  python3-netserver \
  python3-setuptools \
  python3-unixadmin \
  python3-xmlrpc \
"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"
