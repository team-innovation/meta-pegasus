DESCRIPTION = "multidict"
HOMEPAGE = "https://pypi.org/project/multidict/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e74c98abe0de8f798ca609137f9cef4a"

DEPENDS += "python3"

PR = "r1"
SRCNAME = "multidict"

SRC_URI = "https://files.pythonhosted.org/packages/9d/b9/3cf1b908d7af6530209a7a16d71ab2734a736c3cdf0657e3a06d0209811e/multidict-4.3.1.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "20580d5a39570c6ef81664999514ce65"
SRC_URI[sha256sum] = "5ba766433c30d703f6b2c17eb0b6826c6f898e5f58d89373e235f07764952314"

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
