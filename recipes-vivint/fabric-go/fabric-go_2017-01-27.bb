DESCRIPTION = "Build spacemonkey fabric for arm"

PR = "r5"

DEPENDS = "go-cross sqlite3"

inherit go

SRCREV = "${AUTOREV}"
SRC_URI += " \
    http://updateseng.vivint.com/innovation/downloads/fabric-go-${PV}.tar.gz \        
    file://init \
    file://schooner.config \
    file://schooner.procman \
    file://schooner.logrotate \
"

SRC_URI[md5sum] = "1ad529f31d77a2aa45d6218f644aaca1"
SRC_URI[sha256sum] = "1829ea3e77e29c092151d14efecc83bf5fe5ae1599a0ecb4299161762365f20e"

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
