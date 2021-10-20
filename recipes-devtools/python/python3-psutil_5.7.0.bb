DESCRIPTION = "psutil for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e35fd9f271d19d5f742f20a9d1f8bb8b"
SRCNAME = "psutil"
PR = "ml1"

DEPENDS += "python3"

SRC_URI = "https://files.pythonhosted.org/packages/c4/b8/3512f0e93e0db23a71d82485ba256071ebef99b227351f0f5540f744af41/psutil-${PV}.tar.gz"
S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit setuptools3 python3-dir


do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

SRC_URI[md5sum] = "ed7b0f11ed214bcabbe76b7cf52f3ae3"
SRC_URI[sha256sum] = "685ec16ca14d079455892f25bd124df26ff9137664af445563c1bd36629b5e0e"

INSANE_SKIP_${PN} = "True"
