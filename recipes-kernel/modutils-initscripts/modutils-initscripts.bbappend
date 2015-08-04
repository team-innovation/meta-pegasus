FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PR = "r8"

SRC_URI += "\
            file://modules \
           "
do_install_append () {
	install -d ${D}${sysconfdir}
	install -m 0755 ${WORKDIR}/modules ${D}${sysconfdir}
}

