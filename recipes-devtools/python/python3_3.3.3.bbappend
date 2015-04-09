do_configure_append() {
	rm -f ${S}/Makefile.orig
	autoreconf -Wcross --verbose --install --force --exclude=autopoint Modules/_ctypes/libffi || bbnote "_ctypes failed to autoreconf"
}

do_install_append() {
	ln -s -f python3.3m ${D}/usr/include/python3.3
}

SRC_URI_append = "file://enable-ctypes-cross-build.patch"
