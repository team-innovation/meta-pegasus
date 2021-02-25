###
### Change BAGUETTE_PREPEND everything you touch this script
###
BAGUETTE_PREPEND = "-2"

BAGUETTE_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-baguette = "${PR}${BAGUETTE_PREPEND}${BAGUETTE_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-baguette"
INITSCRIPT_NAME_${PN}-baguette = "baguette"
INITSCRIPT_PARAMS_${PN}-baguette = "start 07 S ."

RDEPENDS_${PN}-baguette = "\
	"

REPLACES_${PN}-baguette = "${PN}-pumpernickel"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/baguette/
        ${RSYNC_CMD} ${S}/code/baguette/ ${D}/${INSTALL_DIR}/baguette/
        chmod +x ${D}/${INSTALL_DIR}/baguette/baguette.pyc

	install -d ${D}/opt/2gig/soundfiles/wav
	install -d ${D}/opt/2gig/soundfiles/wav/clicks
	cp -dR ${S}/code/pumpernickel/wav/* ${D}/opt/2gig/soundfiles/wav
	cp -dR ${S}/code/pumpernickel/content/sounds/* ${D}/opt/2gig/soundfiles/wav/clicks
}

FILES_${PN}-baguette = "\
       ${INSTALL_DIR}/baguette/*   		\
	${sysconfdir}/init.d/baguette		\
	${sysconfdir}/procman.d/baguette	\
       "

FILES_${PN}-baguette += "/opt/2gig/soundfiles/*"

