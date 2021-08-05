FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "4"

SRC_URI += "file://abgr-support.patch \
	    file://weston.ini"

do_install_append(){
	install -d ${D}/etc/xdg/weston
	install -m 0600 ${WORKDIR}/weston.ini ${D}/etc/xdg/weston
}
