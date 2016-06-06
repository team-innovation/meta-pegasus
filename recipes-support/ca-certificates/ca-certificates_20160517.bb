#---------------------------

DESCRIPTION = "CA certificate bundles"
HOMEPAGE = "https://curl.haxx.se/ca/cacert.pem"
SECTION = "misc"
PRIORITY = "optional"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=815ca599c9df247a0c7f619bab123dad"
PR = "r2"


# This URL gives us a PEM formatted file of Mozilla.org's certs
# See also https://curl.haxx.se/cvssource/lib/mk-ca-bundle.pl, which downloads
# Mozilla.org's certs, and converts them to pem format.
SRC_URI = "https://curl.haxx.se/ca/cacert.pem \
           file://ca-certificates-subset.crt \
           file://spacemonkey.crt \
           file://LICENSE"
SRC_URI[md5sum] = "782dcde8f5d53b1b9e888fdf113c42b9"
SRC_URI[sha256sum] = "2c6d4960579b0d4fd46c6cbf135545116e76f2dbb7490e24cf330f2565770362"


do_config() {
:
}

do_compile() {
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

do_install() {
	mkdir -p ${D}/opt/2gig/ssl_certs
	cp ${WORKDIR}/ca-certificates-subset.crt ${D}/opt/2gig/ssl_certs/
	cp ${WORKDIR}/cacert.pem ${D}/opt/2gig/ssl_certs/ca-certificates.crt
	cp ${WORKDIR}/ts ${D}/opt/2gig/ssl_certs
	mkdir -p ${D}/etc/ssl
	# /media/extra/ssl_certs gets created at post-install time. For now /etc/ssl/certs will be a dangling symlink
	ln -sf /media/extra/ssl_certs ${D}/etc/ssl/certs
}

pkg_postinst_${PN} () {
#!/bin/sh -e
# Post install to make sure we have the correct setup for sundance
#
if [ x"$D" = "x" ]; then
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
        /bin/rm $MEDIA_DIR/ssl_certs.tar
fi
else
    exit 1
fi
}

FILES_${PN} = "/opt/2gig/ssl_certs/* /etc/ssl/certs"

PACKAGE_ARCH = "all"
PACKAGES = "${PN}"
