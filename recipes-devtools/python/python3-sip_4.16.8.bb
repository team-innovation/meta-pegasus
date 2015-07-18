require python3-sip.inc
PR = "ml0"

do_install_append() {
    install -m 0644 ${S}/siplib/sip.h ${STAGING_INCDIR}/sip.h
}

