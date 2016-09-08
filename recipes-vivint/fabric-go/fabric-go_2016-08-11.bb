DESCRIPTION = "Build skymonkey fabric for arm"

PR = "2"

DEPENDS = "go-cross"

inherit go

SRC_URI += " \
    http://updateseng.vivint.com/innovation/downloads/fabric-go-2016-08-11.tar.gz \
    file://init \
    file://schooner.config \
    file://schooner.procman \
"
SRC_URI[md5sum] = "ca85642136b6dba2f5decfa0220b0704"
SRC_URI[sha256sum] = "d9505be15db08a0d7f6519116cdc0823a2f21c3cd8e9b01fa0c0d27de729e4f8"

LICENSE = "CLOSED"

do_compile() {
    GOPATH=${WORKDIR} go build fabric/bin/schooner
}

do_install() {
    install -d "${D}/${bindir}"
    install -m 0755 "${S}/schooner" "${D}/${bindir}"

    install -d "${D}${sysconfdir}/init.d"
    install -m 0755 "${WORKDIR}/init" "${D}${sysconfdir}/init.d/schooner"

    install "${WORKDIR}/schooner.config" "${D}${sysconfdir}" 

    install -d "${D}${sysconfdir}/procman.d"
    install "${WORKDIR}/schooner.procman" "${D}${sysconfdir}/procman.d/schooner"
}

FILES_${PN} = "\
	${bindir}/schooner \
	${sysconfdir}/schooner.config  \
	${sysconfdir}/init.d/schooner \
	${sysconfdir}/procman.d/schooner \
	"
