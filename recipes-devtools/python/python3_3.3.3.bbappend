do_install_append() {
	# this is wrong but works
	ln -s -f -r ${PKG_CONFIG_SYSROOT_DIR}/usr/include/python3.3m ${PKG_CONFIG_SYSROOT_DIR}/usr/include/python3.3
}
