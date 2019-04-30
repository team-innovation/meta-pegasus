SUMMARY = "Atmel Touchscreen Utility."
DESCRIPTION = "Atmel Touchscreen Utility."
HOMEPAGE = "www.atmel.com"

AUTHOR = "Atmel"
SECTION = "base"
#"Is only provided by Atmel under NDA"
LICENSE = "BSD" 
LIC_FILES_CHKSUM = "file://LICENSE;md5=8b6acde4490765c7b838377ac61e2d2d"

PR = "r3"

DEPENDS="libusb1"


SRC_URI = "git://github.com/atmel-maxtouch/obp-utils.git;protocol=git \
          "
SRCREV = "b782727b91145551e8c8a49d655600d2e74110e0"

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
