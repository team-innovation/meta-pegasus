require python3-sip.inc
PR = "ml0"

inherit native

do_install_append() {
	cp ${S}/siplib/*.h ${STAGING_DIR}/${BUILD_SYS}/usr/include
    install -m 0755 sipgen/sip ${STAGING_DIR}/${BUILD_SYS}/usr/bin
}

