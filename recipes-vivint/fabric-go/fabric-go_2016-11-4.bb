DESCRIPTION = "Build spacemonkey fabric for arm"

PR = "r2"

DEPENDS = "go-cross"

inherit go

SRCREV = "${AUTOREV}"
SRC_URI += " \
    http://updateseng.vivint.com/innovation/downloads/fabric-go-${PV}.tar.gz \        
    file://init \
    file://schooner.config \
    file://schooner.procman \
    file://schooner.logrotate \
"

SRC_URI[md5sum] = "d6da3b49e467e76997d17ecea1ebc8a4"
SRC_URI[sha256sum] = "2b5bbf43c52a27d6ee151a165a3a72e53805fe4c0e7a09d2d7cae3ed62aec697"

LICENSE = "CLOSED"

do_compile() {
    GOPATH=${WORKDIR}/git/go go build fabric/bin/schooner
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
