FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

#file://fix_ssl_include_dir.patch 

SRC_URI += "\
	    file://enable-ctypes-cross-build.patch \
	   "

SRC_URI_remove = "file://remove_sqlite_rpath.patch \
		  file://python-config.patch \
		  file://fix-ast.h-dependency.patch \
		  file://setup2.py.patch \
                  file://configure_tzset.patch \
		  file://avoid-ncursesw-include-path.patch \
	          file://python3-use-CROSSPYTHONPATH-for-PYTHON_FOR_BUILD.patch \
                  file://python3-setup.py-no-host-headers-libs.patch \
                  file://sysconfig.py-add-_PYTHON_PROJECT_SRC.patch \
                  file://setup.py-check-cross_compiling-when-get-FLAGS.patch \
		 "

# Fix ctypes cross compilation
export CROSSPYTHONPATH = "${B}/build/lib.linux-${TARGET_ARCH}-${PYTHON_MAJMIN}:${S}/Lib:${S}/Lib/plat-linux"
export CCSHARED = "-fPIC"

do_install_append() {
	ln -s -f python3.3m ${D}/usr/include/python3.3
}
