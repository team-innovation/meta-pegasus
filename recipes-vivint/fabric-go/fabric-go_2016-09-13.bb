DESCRIPTION = "Build spacemonkey fabric for arm"

PR = "2"

DEPENDS = "go-cross"

inherit go

SRC_URI += " \
    http://updateseng.vivint.com/innovation/downloads/fabric-go-2016-09-13.tar.gz \
    file://init \
    file://schooner.config \
    file://schooner.procman \
"

SRC_URI[md5sum] = "3da5f1edc3c07b9febb1b3d73b24e032"
SRC_URI[sha256sum] = "4f516d400f5493addf428d0bd3fe8d9d65d90682bcca94764c7354eb37f7d6e0"

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
