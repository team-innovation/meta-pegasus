###
### Change TEST_UI_PREPEND everything you touch this script
###
TEST_UI_PREPEND = "-1"

TEST_UI_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-test-ui = "${PR}${TEST_UI_PREPEND}${TEST_UI_REVISION}"
TEST_UI_DEST_DIR = "${INSTALL_DIR}/test_ui"

do_install_append () {
	install -d ${D}/${TEST_UI_DEST_DIR}
        ${RSYNC_CMD} ${S}/code/test_ui/ ${D}/${TEST_UI_DEST_DIR}/
        chmod +x ${D}/${TEST_UI_DEST_DIR}/test_ui.pyo
}

FILES_${PN}-test-ui = "\
       ${TEST_UI_DEST_DIR}/*   \
	${sysconfdir}/init.d/test_ui		\
	${sysconfdir}/procman.d/test_ui		\
       "

