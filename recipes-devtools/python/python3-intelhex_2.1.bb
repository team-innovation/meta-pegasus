SUMMARY = "MessagePack (de)serializer"
HOMEPAGE = "https://pypi.python.org/pypi/msgpack-python/"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=1e431dc8a128348f8b7d3f1f6deb1236"

BBCLASSEXTEND = "native nativesdk"

SRC_URI[md5sum] = "d035c4d747db128304535515c0f4dbb6"
SRC_URI[sha256sum] = "7c1e136efe97672dcf90feed18fc291977d4f5eccf13d124583bec376c0db44c"

inherit pypi setuptools3

do_install_append() {
    sed -i  '1i#!/usr/bin/python3\n' ${D}/${bindir}/bin2hex.py
    sed -i  '1i#!/usr/bin/python3\n' ${D}/${bindir}/hex2bin.py
    sed -i  '1i#!/usr/bin/python3\n' ${D}/${bindir}/hex2dump.py
    sed -i  '1i#!/usr/bin/python3\n' ${D}/${bindir}/hexdiff.py
    sed -i  '1i#!/usr/bin/python3\n' ${D}/${bindir}/hexinfo.py
    sed -i  '1i#!/usr/bin/python3\n' ${D}/${bindir}/hexmerge.py
}
