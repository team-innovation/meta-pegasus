###
### Change INITPUMPERNICKEL_PREPEND everytime you touch this script
###
INITPUMPERNICKEL_PREPEND = "-2"

INITPUMPERNICKEL_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-initpumpernickel = "${PR}${INITPUMPERNICKEL_PREPEND}${INITPUMPERNICKEL_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-pumpernickel"
INITSCRIPT_NAME+${PN}-pumpernickel = "pumpernickel"
INITSCRIPT_PARAMS_${PN}-pumpernickel = "start 07 S ."

FILES_${PN}-initpumpernickel += "\
    ${sysconfdir}/init.d/pumpernickel \
    ${sysconfdir}/procman.d/pumpernickel \
    "
