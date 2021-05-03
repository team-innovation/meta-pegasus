SUMMARY = "JSON library for C++"
HOMEPAGE = "https://github.com/taocpp/json"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=4645f81bc2c1aba6f23c838241edde0b"
PR = "r1"

SRCREV = "a7bac095c8e4e77d88ce807360c2346f55d86b5b"
SRC_URI = "git://github.com/taocpp/json.git;protocol=https \
	   file://taocpp-json.pc"

S = "${WORKDIR}/git"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
	install -d ${D}/${includedir}/taocpp-json
	cp -a ${S}/include ${D}${includedir}/taocpp-json
	install -d ${D}${libdir}/pkgconfig
	install -m 644 ${WORKDIR}/taocpp-json.pc ${D}/${libdir}/pkgconfig

}

FILES_${PN}-dev = "${includedir}/taocpp-json/* ${libdir}/pkgconfig"
ALLOW_EMPTY_${PN} = "1"

BBCLASSEXTEND = "native nativesdk"
