SUMMARY = "A type-safe union for C++"
HOMEPAGE = "https://github.com/martinmoene/variant-lite"
LICENSE = "BSL-1.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE.txt;md5=e4224ccaecb14d942c71d31bef20d78c"
PR = "r1"

SRCREV = "b0aa7a4f6fcf0bcdbf01ecc426c6cb4856640a40"
SRC_URI = "git://github.com/martinmoene/variant-lite.git;protocol=https \
	   file://variant-lite.pc"

S = "${WORKDIR}/git"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
	install -d ${D}/${includedir}/variant-lite/
	cp -a ${S}/include/ ${D}/${includedir}/variant-lite

	install -d ${D}/${libdir}/pkgconfig
	install -m 644 ${WORKDIR}/variant-lite.pc ${D}/${libdir}/pkgconfig
}

FILES_${PN}-dev = "${includedir}/variant-lite/* ${libdir}/pkgconfig"
ALLOW_EMPTY_${PN} = "1"

BBCLASSEXTEND = "native nativesdk"
