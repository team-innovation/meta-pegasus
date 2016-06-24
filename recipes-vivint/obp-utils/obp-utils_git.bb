SUMMARY = "Atmel Touchscreen Utility."
DESCRIPTION = "Atmel Touchscreen Utility."
HOMEPAGE = "www.atmel.com"

AUTHOR = "Atmel"
SECTION = "base"
LICENSE = "Is only provided by Atmel under NDA"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8b6acde4490765c7b838377ac61e2d2d"

PR = "r2"

SRC_URI = "git://github.com/atmel-maxtouch/obp-utils.git;protocol=git \
           file://obp-utils-makefile.patch \
          "
SRC_URI[md5sum] = "a86b93c9e7c81f1108598f4e7d81dee2"
SRC_URI[sha256sum] = "82d4e2f05703cc85e661775610e5940e00a49cc230a866271a31118fcf4ce076"

SRCREV = "a6b224001bf7d9a40d1ed78994e8d6e9d504204e"
S = "${WORKDIR}/git"

PV = "1.0-a6b2"

inherit autotools pkgconfig

export BUILD_SYS
export HOST_SYS
export STAGING_LIBDIR
export STAGING_INCDIR

do_install() {
        install -d ${D}${bindir}
        install -m 0755 mxt-app ${D}${bindir}/mxt-app
}
