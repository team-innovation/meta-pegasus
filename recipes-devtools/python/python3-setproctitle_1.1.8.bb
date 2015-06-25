DESCRIPTION = "Set Proc Title Support for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "PSF"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=a4a609ce8a6621f030a70a3a5f2617c1"
SRCNAME = "setproctitle"
PR = "ml2"

SRC_URI = "http://pypi.python.org/packages/source/s/${SRCNAME}/${SRCNAME}-${PV}.tar.gz \
  file://setproctitle_33.py"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils3

DEPENDS += "python"
DEPENDS_virtclass-native += "python-native"

RDEPENDS_${PN} = "python-core"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

do_package_qa() {
    echo "Skipping QA ..."
}

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
    install -m 0644 ${WORKDIR}/setproctitle_33.py ${D}/${libdir}/${PYTHON_DIR}/site-packages/setproctitle.py
}

DEPENDS_${PN} = "\
  python3-distutils \
"

SRC_URI[md5sum] = "728f4c8c6031bbe56083a48594027edd"
SRC_URI[sha256sum] = "b564cf6488217c7a4632a9fe646fc3a3bea2f9712b4e667e9632b870d1a58211"

INSANE_SKIP_${PN} = "True"
