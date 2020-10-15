FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	file://compile_fixes.patch \
	file://iotivity-bridging.pc \
"

do_install_append () {
	install -d ${D}${libdir}/pkgconfig
	install -m 0644 ${WORKDIR}/iotivity-bridging.pc ${D}${libdir}/pkgconfig
}

FILES_${PN}-dev += "${libdir}/pkgconfig"

# Iotivity needs optimization for FORTIFY_SOURCES flag
CC_append = " -Os"
CXX_append = " -Os"
CPP_append = " -Os"
