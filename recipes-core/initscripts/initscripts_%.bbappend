FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PR_append = "-20"

SRC_URI += "file://finish.sh    \
	    file://hostname.sh  \
            "
do_install_append () {
    install -m 0755    ${WORKDIR}/finish.sh   ${D}${sysconfdir}/init.d
    update-rc.d -r ${D} finish.sh start 99 S .
}
