DESCRIPTION = "A collection of cryptographic algorithms and protocols"
SECTION = "devel/python"
PRIORITY = "optional"
DEPENDS = "gmp autoconf"
SRCNAME = "pycrypto"
LICENSE = "pycrypto"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=35f354d199e8cb7667b059a23578e63d"
PR = "ml1"

DEPENDS += "python3"
DEPENDS_virtclass-native += "python3-native"

SRC_URI = "http://ftp.dlitz.net/pub/dlitz/crypto/pycrypto/pycrypto-${PV}.tar.gz"
S = "${WORKDIR}/pycrypto-2.5"

inherit distutils3

do_configure_prepend() {
	sed -i 's|ac_cv_func_malloc_0_nonnull=no|ac_cv_func_malloc_0_nonnull=yes|' configure
	sed -i 's|cmd = "sh configure"|cmd = "sh configure --host=${TARGET_ARCH}"|g' setup.py
	sed -i "s|include_dirs=\['src/','/usr/include/'\]|include_dirs=\['src/'\]|" setup.py
}


# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

do_package_qa() {
    echo "Skipping QA ..."
}

inherit distutils

DEPENDS_${PN} = "\
  python3-distutils \
"

SRC_URI[md5sum] = "783e45d4a1a309e03ab378b00f97b291"
SRC_URI[sha256sum] = "e950a78184e2a7defccf5d45e0c29c1e9edeb29984433f0d110a21e9631e38de"

INSANE_SKIP_${PN} = "True"
