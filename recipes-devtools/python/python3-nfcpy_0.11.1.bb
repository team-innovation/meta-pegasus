DESCRIPTION = "Python NFC Library"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "EUPL-1.1"
LIC_FILES_CHKSUM = "file://README.rst;md5=22688ade7b8f42bae952b0400da27a09"
SRCNAME = "python-nfcpy"
PR = "m1"

SRCREV = "89816998694789617f1ef7461f3bce2e806cf69e9c9cc9cc3a2edf516de3"
SRC_URI += "file://nfcpy-2to3-plus-byte-manip-fixes.patch \
"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit setuptools3 pypi

export HOST_SYS
export BUILD_SYS

do_compile_append() {
    # 2to3 -w ${S}/build
    cp -r ${S}/build/* ${S}/
}

SRC_URI[md5sum] = "c3844d44c9295416e8477274e98c81f7"
SRC_URI[sha256sum] = "7068f781408ebbc80246bddb417fb98662de15ffe5620a1202f027de522f0b5a"
