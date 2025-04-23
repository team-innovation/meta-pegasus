do_install_append () {
	install -d ${D}/${INSTALL_DIR}/global_conf
	${RSYNC_CMD} ${S}/code/global_conf/ ${D}/${INSTALL_DIR}/global_conf
}

FILES_${PN}-global-conf = "\
	${INSTALL_DIR}/global_conf/* \
"
