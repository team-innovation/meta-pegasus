SUMMARY = "Download, build, install, upgrade, and uninstall Python packages"
HOMEPAGE = "https://pypi.python.org/pypi/setuptools"
SECTION = "devel/python"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://setup.py;beginline=146;endline=146;md5=36f18291ba4f8f830401abf9e5fe944f"

SRCNAME = "setuptools"

SRC_URI = "https://files.pythonhosted.org/packages/source/s/${SRCNAME}/${SRCNAME}-${PV}.zip"

SRC_URI[md5sum] = "74663b15117d9a2cc5295d76011e6fd1"
SRC_URI[sha256sum] = "62074589522a798da243f47348f38020d55b6c945652e2f2c09d3a96299812b7"

UPSTREAM_CHECK_URI = "https://pypi.python.org/pypi/setuptools"

S = "${WORKDIR}/${SRCNAME}-${PV}"

DEPENDS += "python3"
DEPENDS_class-native += "python3-native"

inherit distutils3

DISTUTILS_INSTALL_ARGS += "--install-lib=${D}${PYTHON_SITEPACKAGES_DIR}"

do_install_prepend() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
}

# The installer puts the wrong path in the setuptools.pth file.  Correct it.
do_install_append() {
    rm ${D}${PYTHON_SITEPACKAGES_DIR}/setuptools.pth
    mv ${D}${bindir}/easy_install ${D}${bindir}/easy3_install
    echo "./${SRCNAME}-${PV}-py${PYTHON_BASEVERSION}.egg" > ${D}${PYTHON_SITEPACKAGES_DIR}/setuptools.pth
}

RDEPENDS_${PN} = "\
  python3-distutils \
  python3-compression \
"
RDEPENDS_${PN}_class-target = "\
  python3-ctypes \
  python3-distutils \
  python3-email \
  python3-importlib \
  python3-numbers \
  python3-compression \
  python3-shell \
  python3-subprocess \
  python3-textutils \
  python3-pkgutil \
  python3-threading \
  python3-misc \
  python3-unittest \
  python3-xml \
"
BBCLASSEXTEND = "native"
