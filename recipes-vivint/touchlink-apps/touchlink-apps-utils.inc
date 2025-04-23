DEPENDS += "python3-core python3"
RDEPENDS_touchlink-apps-utils += "python3-core python3 bash"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/utils/
	${RSYNC_CMD} ${S}/code/utils/ ${D}/${INSTALL_DIR}/utils/
	chmod +x ${D}/${INSTALL_DIR}/utils/*
}

FILES_${PN}-utils = "\
    ${INSTALL_DIR}/utils/*
"
