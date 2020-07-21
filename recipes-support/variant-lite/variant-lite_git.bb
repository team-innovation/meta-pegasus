SUMMARY = "A type-safe union for C++"
HOMEPAGE = "https://github.com/martinmoene/variant-lite"
LICENSE = "BSL-1.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE.txt;md5=e4224ccaecb14d942c71d31bef20d78c"
PR = "r1"

SRCREV = "b0aa7a4f6fcf0bcdbf01ecc426c6cb4856640a40"
SRC_URI = "git://github.com/martinmoene/variant-lite.git;protocol=https"

S = "${WORKDIR}/git"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
	install -d ${D}/${includedir}/variant-lite/
	cp -a ${S}/include/ ${D}/${includedir}/variant-lite
}

FILES_${PN} = "${includedir}/variant-lite/*"

BBCLASSEXTEND = "native nativesdk"
