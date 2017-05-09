###
### Change INITPUMPERNICKEL_PREPEND everytime you touch this script
###
INITPUMPERNICKEL_PREPEND = "-2"

INITPUMPERNICKEL_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-initpumpernickel = "${PR}${INITPUMPERNICKEL_PREPEND}${INITPUMPERNICKEL_REVISION}"

FILES_${PN}-initpumpernickel += "\
    ${sysconfdir}/init.d/pumpernickel \
    ${sysconfdir}/procman.d/pumpernickel \
    "
