SUMMARY = "vis web visualizer."
DESCRIPTION = "Allow us to see the visual representation of the network. Modified with scripts from Vivint"
HOMEPAGE = "https://github.com/almende/vis"

AUTHOR = "Almende B.V. and Contributors"
SECTION = "base"
LICENSE = "MIT" 
LIC_FILES_CHKSUM = "file://LICENSE-MIT;md5=d147f14c7e6b7edc53fc9053bef1e483"

PR = "r8"

DEPENDS="vivutils"

SRC_URI = "https://github.com/almende/vis/archive/v${PV}.tar.gz \
	file://networkDot.html \
	file://oui.csv \
	file://node_map.html \
	file://svglib.js \
	file://graph_me.cgi \
	file://iperf_me.cgi \
	file://build_dot_graph.py \
	file://get_mesh_info.py \
          "
SRC_URI[md5sum] = "fe2f429b04e75958b5079d5026af23a2"
SRC_URI[sha256sum] = "055f17340e0bfa4eccc4d4dadd625e19d768e43c65d82228425d4d79c0d3f415"

SERVER_ROOT = "/srv/www/network"

FILES_${PN} = "${SERVER_ROOT}/vis/* \
${SERVER_ROOT}/cgi-bin/* \
${SERVER_ROOT}/oui.csv \
${SERVER_ROOT}/svglib.js \
${SERVER_ROOT}/node_map.html \
/usr/bin/*"

do_configure() {
}

do_compile() {
}

do_install() {
	install -d ${D}/${SERVER_ROOT}/vis
	install -d ${D}/${SERVER_ROOT}/cgi-bin
	install -d ${D}/usr/bin
	cp -a ${S}/* ${D}/${SERVER_ROOT}/vis
        install -m 0755 ${WORKDIR}/networkDot.html ${D}/${SERVER_ROOT}/vis/test
        install -m 0755 ${WORKDIR}/oui.csv ${D}/${SERVER_ROOT}
        install -m 0755 ${WORKDIR}/svglib.js ${D}/${SERVER_ROOT}
        install -m 0755 ${WORKDIR}/node_map.html ${D}/${SERVER_ROOT}
        install -m 0755 ${WORKDIR}///graph_me.cgi ${D}/${SERVER_ROOT}/cgi-bin
        install -m 0755 ${WORKDIR}///iperf_me.cgi ${D}/${SERVER_ROOT}/cgi-bin
        install -m 0755 ${WORKDIR}///build_dot_graph.py ${D}/usr/bin
        install -m 0755 ${WORKDIR}///get_mesh_info.py ${D}/usr/bin
}
