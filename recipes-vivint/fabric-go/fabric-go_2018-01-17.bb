DESCRIPTION = "Build spacemonkey fabric for arm"

PR = "r9"

DEPENDS = "go-cross sqlite3"

inherit go

SRC_URI += " \
    http://updateseng.vivint.com/innovation/downloads/fabric-go-${PV}.tar.bz2 \        
    file://init \
    file://schooner.config \
    file://schooner.procman \
    file://schooner.logrotate \
"

SRC_URI[md5sum] = "c5338fcd1e0f3f37eb807aab039e83b2"
SRC_URI[sha256sum] = "7f933f1d755a4903b5e9c08cb4c91ac5c54228c60aab375d33fdc0e4b27121d3"

LICENSE = "CLOSED"

do_compile() {
    CGO_ENABLED=1 GOPATH=${WORKDIR} go build fabric/bin/schooner
}

do_install() {
    install -d "${D}/${bindir}"
    install -m 0755 "${S}/schooner" "${D}/${bindir}"

    install -d "${D}${sysconfdir}/init.d"
    install -m 0755 "${WORKDIR}/init" "${D}${sysconfdir}/init.d/schooner"

    install "${WORKDIR}/schooner.config" "${D}${sysconfdir}" 

    install -d "${D}${sysconfdir}/procman.d"
    install "${WORKDIR}/schooner.procman" "${D}${sysconfdir}/procman.d/schooner"

    install -d "${D}${sysconfdir}/logrotate.d"
    install -m 0600 "${WORKDIR}/schooner.logrotate" "${D}${sysconfdir}/logrotate.d/schooner"
}

FILES_${PN} = "\
	${bindir}/schooner \
	${sysconfdir}/schooner.config  \
	${sysconfdir}/init.d/schooner \
	${sysconfdir}/procman.d/schooner \
    ${sysconfdir}/logrotate.d/schooner \ 
	"
