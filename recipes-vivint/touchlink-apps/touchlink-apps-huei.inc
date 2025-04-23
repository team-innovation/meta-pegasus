do_install_append () {
    install -d ${D}/${INSTALL_DIR}/huei/
    ${RSYNC_CMD} ${S}/code/huei/dist/ ${D}/${INSTALL_DIR}/huei/
}

FILES_${PN}-huei = "\
    ${INSTALL_DIR}/huei/* \
"
