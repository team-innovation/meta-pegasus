do_install_append() {
	# this is wrong but works
	ln -s -f python3.3m ${D}/usr/include/python3.3
}
