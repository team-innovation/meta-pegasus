do_install_append() {
	# this is wrong but works
	cd ${D}/usr/include
	ln -s -f python3.3m python3.3
	cd -
}
