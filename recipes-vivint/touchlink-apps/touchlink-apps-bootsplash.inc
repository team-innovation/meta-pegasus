###
### Change BOOTSPLASH_PREPEND everything you touch this script
###
BOOTSPLASH_PREPEND = "-1"

BOOTSPLASH_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-bootsplash = "${PR}${BOOTSPLASH_PREPEND}${BOOTSPLASH_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-bootsplash"
INITSCRIPT_NAME_${PN}-bootsplash = "bootsplash.sh"
INITSCRIPT_PARAMS_${PN}-bootsplash = "start 07 S ."

FILES_${PN}-bootsplash = "${sysconfdir}/init.d/bootsplash.sh"
