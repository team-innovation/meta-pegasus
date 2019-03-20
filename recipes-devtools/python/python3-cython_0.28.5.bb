DESCRIPTION = "Cython Compiler"
HOMEPAGE = "http://cython.org"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=e23fadd6ceef8c618fc1c65191d846fa"

PR = "r1"
PROVIDES = "python3-cython"

SRC_URI = "https://files.pythonhosted.org/packages/21/89/ca320e5b45d381ae0df74c4b5694f1471c1b2453c5eb4bac3449f5970481/Cython-0.28.5.tar.gz"

SRC_URI[md5sum] = "0cb620e1259818e4ecc1a056e8c3a8be"
SRC_URI[sha256sum] = "b64575241f64f6ec005a4d4137339fb0ba5e156e826db2fdb5f458060d9979e0"

SRCNAME ="Cython-${PV}"
S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
