DESCRIPTION = "Build spacemonkey fabric for arm"

PR = "r1"

DEPENDS = "go-cross"

inherit go

GIT_SSH_COMMAND="ssh -F fabric_config"

SRCREV = "${AUTOREV}"
SRC_URI += " \
    gitsm://git.vivint.com/katamari;protocol=git;branch=release/current-panel \
    file://init \
    file://schooner.config \
    file://schooner.procman \
"
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
}

FILES_${PN} = "\
	${bindir}/schooner \
	${sysconfdir}/schooner.config  \
	${sysconfdir}/init.d/schooner \
	${sysconfdir}/procman.d/schooner \
	"
