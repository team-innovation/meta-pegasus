###
### Change INITPUMPERNICKEL_PREPEND everytime you touch this script
###
INITBOB_PREPEND = "-3"

INITBOB_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-initbob = "${PR}${INITPUMPERNICKEL_PREPEND}${INITPUMPERNICKEL_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-initbob"
INITSCRIPT_NAME_${PN}-initbob = "initbob"
INITSCRIPT_PARAMS_${PN}-initbob = "start 07 S ."

FILES_${PN}-initbob += "\
    ${sysconfdir}/init.d/bob \
    ${sysconfdir}/init.d/initbob \
    ${sysconfdir}/procman.d/bob \ 
    "
