#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
	ln -s -f python3.4m ${D}/usr/include/python3.4
}
