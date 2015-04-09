###
### Change VOCAB_PREPEND everything you touch this script
###
VOCAB_PREPEND = "-1"

VOCABULARY_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-vocabulary = "${PR}${VOCAB_PREPEND}${VOCABULARY_REVISION}"

do_install_append() {
	# vocabulary
        ${RSYNC_CMD} ${S}/sounds/ ${D}/${INSTALL_DIR}/sounds
}

FILES_${PN}-vocabulary = "\
       ${INSTALL_DIR}/sounds/*"

             
