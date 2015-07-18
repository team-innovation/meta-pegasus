#do_configure_append() {
#	rm -f ${S}/Makefile.orig
#	autoreconf --verbose --install --force --exclude=autopoint Modules/_ctypes/libffi || bbnote "_ctypes failed to autoreconf"
#}

do_install_append() {
	ln -s -f python3.3m ${D}${includedir}/python3.3
}
