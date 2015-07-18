#FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
	    file://fix_ssl_include_dir.patch \
	   "

# Fix ctypes cross compilation
export CROSSPYTHONPATH = "${B}/build/lib.linux-${TARGET_ARCH}-${PYTHON_MAJMIN}:${S}/Lib:${S}/Lib/plat-linux"

do_install_append() {
	ln -s -f python3.3m ${D}/usr/include/python3.3
}
