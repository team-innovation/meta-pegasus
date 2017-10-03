###
### Change TEST_UI_PREPEND everything you touch this script
###
TEST_UI_PREPEND = "-2"

TEST_UI_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-test-ui = "${PR}${TEST_UI_PREPEND}${TEST_UI_REVISION}"


FILES_${PN}-test-ui = "\
	${sysconfdir}/init.d/test_ui \
	${sysconfdir}/procman.d-fcc/test_ui \
	"

