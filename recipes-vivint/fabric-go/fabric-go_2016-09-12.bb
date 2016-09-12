DESCRIPTION = "Build spacemonkey fabric for arm"

PR = "2"

DEPENDS = "go-cross"

inherit go

SRC_URI += " \
    http://updateseng.vivint.com/innovation/downloads/fabric-go-2016-09-12.tar.gz \
    file://init \
    file://schooner.config \
    file://schooner.procman \
"

SRC_URI[md5sum] = "1e0756120fe23af19e71fdf31e27bea5"
SRC_URI[sha256sum] = "2854f074e4c40ee264e14fc8f913fb516207eaa5d959a82a675cf5da33d598fa"

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
