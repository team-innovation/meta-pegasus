DESCRIPTION = "Client side implementation of rsync algorithm"
HOMEPAGE = "http://zsync.moria.org.uk"
DEPENDS = "popt curl"
RDEPENDS_${PN} = "curl"
SECTION = "net"
LIC_FILES_CHKSUM = "file://COPYING;md5=71c0ac4d86266533509aa0825b8d323c"
LICENSE = "Artistic-2.0"
PR = "r7"

BBCLASSEXTEND = "native"
NATIVE_INSTALL_WORKS = "1"

SRC_URI = "http://${UPDATESENG}/downloads/zsync-${PV}.tar.bz2 \
	file://0001-New-version-with-libcurl-updates-and-new-autotools.patch \
	file://0002-curl-auth.patch \
	file://0003-enable-progress-no-tty.patch \
"


S = "${WORKDIR}/zsync-${PV}"

do_configure() {
   ./configure --host=${TARGET_SYS}
}

do_compile() {
   oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 755 ${S}/zsync ${D}${bindir}
    install -m 755 ${S}/zsyncmake ${D}${bindir}
}

FILES_${PN} = "${bindir}/zsync \
	       ${bindir}/zsyncmake"

SRC_URI[md5sum] = "862f90bafda118c4d3c5ee6477e50841"
SRC_URI[sha256sum] = "0b9d53433387aa4f04634a6c63a5efa8203070f2298af72a705f9be3dda65af2"

