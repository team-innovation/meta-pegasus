FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://finish.sh    \
            "
do_install_append () {
    install -m 0755    ${WORKDIR}/finish.sh   ${D}${sysconfdir}/init.d
    update-rc.d -r ${D} finish.sh start 99 S .
}
