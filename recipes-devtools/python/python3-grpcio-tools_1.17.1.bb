DESCRIPTION = "grpcio-tools"
HOMEPAGE = "https://pypi.org/project/grpcio-tools/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://MANIFEST.in;md5=4e1da04207560d860fc7e2eeb805727f"

DEPENDS += "python3 python3-cython"
RDEPENDS_${PN} = "python3-protobuf python3-six"

PR = "r0"

SRCNAME = "grpcio-tools"
SRC_URI = "https://files.pythonhosted.org/packages/7b/a7/0291954302a82c6712b7c7988b8d7bcaa2a4c5f4ec42fb5a35b79209d13b/${SRCNAME}-${PV}.tar.gz"
SRC_URI[md5sum] = "f51f965ca452cc67388baaf9c4f6a38a"
SRC_URI[sha256sum] = "cbf98c7623366170c2049515f0a5bbe82af7d09f987a227d5760763e5e3646b2"

S = "${WORKDIR}/${SRCNAME}-${PV}"

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
