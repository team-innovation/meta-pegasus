###
### Change TEST_DAEMON_PREPEND everything you touch this script
###
TEST_DAEMON_PREPEND = "-2"

TEST_DAEMON_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-test-daemon = "${PR}${TEST_DAEMON_PREPEND}${TEST_DAEMON_REVISION}"
TEST_DAEMON_DEST_DIR = "${INSTALL_DIR}/test_daemon"

do_install_append () {
	install -d ${D}/${TEST_DAEMON_DEST_DIR}
        ${RSYNC_CMD} ${S}/code/test_daemon/ ${D}/${TEST_DAEMON_DEST_DIR}
        chmod +x ${D}/${TEST_DAEMON_DEST_DIR}/test_daemon.pyo
}

FILES_${PN}-test-daemon = "\
       ${TEST_DAEMON_DEST_DIR}/*   \
	${sysconfdir}/init.d/test_daemon		\
	${sysconfdir}/procman.d-fcc/test_daemon		\
       "

