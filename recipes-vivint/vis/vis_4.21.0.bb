SUMMARY = "vis web visualizer."
DESCRIPTION = "Allow us to see the visual representation of the network."
HOMEPAGE = "https://github.com/almende/vis"

AUTHOR = "Atmel"
SECTION = "base"
LICENSE = "MIT" 
LIC_FILES_CHKSUM = "file://LICENSE-MIT;md5=d147f14c7e6b7edc53fc9053bef1e483"

PR = "r0"

DEPENDS="vivutils"

SRC_URI = "https://github.com/almende/vis/archive/v${PV}.tar.gz \
	file://networkDot.html \
	file://graph_me.sh \
	file://build_dot_graph.py \
	file://get_mesh_info.py \
          "
SRC_URI[md5sum] = "fe2f429b04e75958b5079d5026af23a2"
SRC_URI[sha256sum] = "055f17340e0bfa4eccc4d4dadd625e19d768e43c65d82228425d4d79c0d3f415"

FILES_${PN} = "/srv/www/vis/* \
/srv/www/cgi-bin/* \
/usr/bin/*"

do_configure() {
}

do_compile() {
}

do_install() {
	install -d ${D}/srv/www/vis
	install -d ${D}/srv/www/cgi-bin
	install -d ${D}/usr/bin
	cp -a ${S}/* ${D}/srv/www/vis
        install -m 0755 ${WORKDIR}/networkDot.html ${D}/srv/www/vis/test
        install -m 0755 ${WORKDIR}///graph_me.sh ${D}/srv/www/cgi-bin
        install -m 0755 ${WORKDIR}///build_dot_graph.py ${D}/usr/bin
        install -m 0755 ${WORKDIR}///get_mesh_info.py ${D}/usr/bin
}
