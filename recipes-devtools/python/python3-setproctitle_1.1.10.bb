DESCRIPTION = "Set Proc Title Support for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "PSF"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=a1f1f516ff59e8899115754de850190f"
SRCNAME = "setproctitle"
PR = "ml2"

SRC_URI = "https://pypi.python.org/packages/source/s/${SRCNAME}/${SRCNAME}-${PV}.tar.gz \
  file://setproctitle_33.py"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools3 python3-dir

DEPENDS += "python3"

RDEPENDS_${PN} = "python3-core"

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

SRC_URI[md5sum] = "2dcdd1b761700a5a13252fea3dfd1977"
SRC_URI[sha256sum] = "6283b7a58477dd8478fbb9e76defb37968ee4ba47b05ec1c053cb39638bd7398"

INSANE_SKIP_${PN} = "True"
