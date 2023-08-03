FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

LOGPASS = "\$1\$zRCmwE.F\$WkHQsRVERc5seiAf/y8ND."
CLIPPASS = "\$1\$E9/UFeOn\$MK2G6qTsxwDgtIvCWGEwK0"

USERADD_PARAM_${PN} += "; --home-dir=/media/extra/log_upload --no-create-home -g loguser -G ftp \
                          --password '${LOGPASS}' --shell /sbin/nologin loguser; \
                         --home-dir=/media/clips/ftp --no-create-home -g clipuser -G ftp \
                         --password '${CLIPPASS}' --shell /sbin/nologin clipuser"
GROUPADD_PARAM_${PN} += "; loguser; clipuser"

SRC_URI += "file://vsftpd.logrotate"

do_install_append () {
    install -d "${D}${sysconfdir}/logrotate.d"
    install -m 0600 "${WORKDIR}/vsftpd.logrotate" "${D}${sysconfdir}/logrotate.d/vsftpd"

    sed -i "/pam.shells/s/^#*/#/" ${D}${sysconfdir}/pam.d/vsftpd
}
