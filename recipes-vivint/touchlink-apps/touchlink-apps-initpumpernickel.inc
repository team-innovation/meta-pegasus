###
### Change INITPUMPERNICKEL_PREPEND everytime you touch this script
###
INITPUMPERNICKEL_PREPEND = "-3"

INITPUMPERNICKEL_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-initpumpernickel = "${PR}${INITPUMPERNICKEL_PREPEND}${INITPUMPERNICKEL_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-initpumpernickel"
INITSCRIPT_NAME_${PN}-initpumpernickel = "initpumpernickel"
INITSCRIPT_PARAMS_${PN}-initpumpernickel = "start 30 2 3 4 5 ."

FILES_${PN}-initpumpernickel += "\
    ${sysconfdir}/init.d/pumpernickel \
    ${sysconfdir}/init.d/initpumpernickel \
    "
