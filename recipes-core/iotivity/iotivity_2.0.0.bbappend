FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://RapidJSON.pc \
	file://tinycbor.pc \
	file://libcjson.pc \
	file://iotivity-bridging.pc \
"

do_install_prepend () {
	install -d ${D}${libdir}/pkgconfig
	install -m 0644 ${WORKDIR}/RapidJSON.pc ${D}${libdir}/pkgconfig
	install -m 0644 ${WORKDIR}/tinycbor.pc ${D}${libdir}/pkgconfig
	install -m 0644 ${WORKDIR}/libcjson.pc ${D}${libdir}/pkgconfig
	install -m 0644 ${WORKDIR}/iotivity-bridging.pc ${D}${libdir}/pkgconfig
}

FILES_${PN}-bridging-dev += "${libdir}/pkgconfig/*"
