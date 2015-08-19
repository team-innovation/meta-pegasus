FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "r1"

#file://fix_ssl_include_dir.patch 

SRC_URI += "\
	    file://enable-ctypes-cross-build.patch \
	    file://configure_tzset.patch \
	   "

SRC_URI_remove = "file://remove_sqlite_rpath.patch \
		  file://python-config.patch \
		  file://fix-ast.h-dependency.patch \
		  file://setup2.py.patch \
		  file://avoid-ncursesw-include-path.patch \
	          file://python3-use-CROSSPYTHONPATH-for-PYTHON_FOR_BUILD.patch \
                  file://python3-setup.py-no-host-headers-libs.patch \
                  file://sysconfig.py-add-_PYTHON_PROJECT_SRC.patch \
                  file://setup.py-check-cross_compiling-when-get-FLAGS.patch \
		 "

# Fix ctypes cross compilation
export CROSSPYTHONPATH = "${B}/build/lib.linux-${TARGET_ARCH}-${PYTHON_MAJMIN}:${S}/Lib:${S}/Lib/plat-linux"
export CCSHARED = "-fPIC"

# The configure file was patched, DO NOT autoreconf
do_configure() {
        oe_runconf LD_FLAGS="-L${STAGING_LIBDIR}"
#       oe_runconf
# Disable BLUETOOTH SUPPORT
        sed -i 's,#define HAVE_BLUETOOTH_BLUETOOTH_H 1,#undef HAVE_BLUETOOTH_BLUETOOTH_H,' pyconfig.h
        sed -i 's,#undef PY_FORMAT_LONG_LONG,#define PY_FORMAT_LONG_LONG 'll',' pyconfig.h
}


do_install_append() {
	ln -s -f python3.3m ${D}/usr/include/python3.3
}
