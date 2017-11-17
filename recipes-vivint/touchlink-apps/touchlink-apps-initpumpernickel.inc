###
### Change INITPUMPERNICKEL_PREPEND everytime you touch this script
###
INITPUMPERNICKEL_PREPEND = "-3"

INITPUMPERNICKEL_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-initpumpernickel = "${PR}${INITPUMPERNICKEL_PREPEND}${INITPUMPERNICKEL_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-initpumpernickel"
INITSCRIPT_NAME+${PN}-initpumpernickel = "pumpernickel"
INITSCRIPT_PARAMS_${PN}-initpumpernickel = "start 07 S ."

FILES_${PN}-initpumpernickel += "\
    ${sysconfdir}/init.d/pumpernickel \
    ${sysconfdir}/procman.d/pumpernickel \
    "
