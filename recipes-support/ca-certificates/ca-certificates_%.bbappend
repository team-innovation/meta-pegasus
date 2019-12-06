FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://ca-certificates-subset.crt \
           file://spacemonkey.crt \
	   "

do_compile_append() {
        echo >> ${WORKDIR}/cacert.pem
        cat ${WORKDIR}/spacemonkey.crt >> ${WORKDIR}/cacert.pem
        # We create a timestamp file so that post-install can know whether to
        # overwrite the contents of /media/extra/ssl_certs. We can't use file
        # timestamps, because the panel's time and file timestamps may be way off.
        #
        # This allows panel upgrades to update the certificates, but only if the
        # upgrade copy is newer than what exists in /media/extra/ssl_certs.
        #
        # FUTURE TODO: It may be an improvement to manually update the ts file when
        # we update the bundle of CA certificates. That way, a panel upgrade will
        # only update the certificates if they are truly different and newer than
        # what the platform configured.
        date "+%s" > ${WORKDIR}/ts
}

do_install_append() {
	install -d ${D}/opt/2gig/ssl_certs

	install -m 644 ${WORKDIR}/ca-certificates-subset.crt ${D}/opt/2gig/ssl_certs/
	install -m 644 ${WORKDIR}/cacert.pem ${D}/opt/2gig/ssl_certs/ca-certificates.crt
        install -m 644 ${WORKDIR}/ts ${D}/opt/2gig/ssl_certs
        # /media/extra/ssl_certs gets created at post-install time. For now /etc/ssl/certs will be a dangling symlink
        ln -sf /media/extra/ssl_certs ${D}/${sysconfdir}/ssl/certs
}

pkg_postinst_ontarget_${PN} () {
#!/bin/sh -e
# Post install to make sure we have the correct setup for sundance
#
    DEFAULT_CERTS_DIR=/opt/2gig/ssl_certs
    MEDIA_DIR=/media/extra
    MEDIA_SSL_CERT_DIR=$MEDIA_DIR/ssl_certs
    if [ ! -d $MEDIA_SSL_CERT_DIR ]; then
        echo "Creating $MEDIA_SSL_CERT_DIR"
        mkdir -p $MEDIA_SSL_CERT_DIR
    else
        echo "$MEDIA_SSL_CERT_DIR already exists. Won't install defaults from $DEFAULT_CERTS_DIR"
    fi

    # Only upgrade the copy of certs in /media/extra/ssl_certs if what the panel
    # has is newer.
    DEFAULT_CERTS_TIMESTAMP=$(cat $DEFAULT_CERTS_DIR/ts 2> /dev/null || echo "0")
    MEDIA_CERTS_TIMESTAMP=$(cat $MEDIA_SSL_CERT_DIR/ts 2> /dev/null || echo "0")
    if [ $DEFAULT_CERTS_TIMESTAMP -gt $MEDIA_CERTS_TIMESTAMP ]; then
        for f in $(cd $DEFAULT_CERTS_DIR ; find . -maxdepth 1 -type f) ; do
            cp $DEFAULT_CERTS_DIR/$f $MEDIA_SSL_CERT_DIR/
        done
        # Remove the tar file that was previously put in place by the platform --
        # so that the property doesn't showing the wrong
        # certificate_bundle_version_sha224_base64 to the platform.
     /bin/rm -f $MEDIA_DIR/ssl_certs.tar
    fi
}

FILES_${PN} += "/opt/2gig/ssl_certs/* /etc/ssl/certs"

