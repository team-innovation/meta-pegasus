DESCRIPTION = "Build spacemonkey fabric for arm"

PR = "r10"
PV = "1.0-85158766b1544bc088977742494cfad4db0acc4c"

DEPENDS = "sqlite3"

SRC_URI += " \
    file://init \
    file://schooner.config \
    file://schooner.procman \
    file://schooner.logrotate \
    file://schooner \
    file://schooner_arm64 \
"

LICENSE = "CLOSED"

do_compile[noexec] = "1"

do_install() {
    install -d "${D}/${bindir}"
    if [ ${HOST_SYS} != "aarch64-poky-linux" ]; then
       install -m 0755 "${WORKDIR}/schooner" "${D}/${bindir}"
    else
       install -m 0755 "${WORKDIR}/schooner_arm64" "${D}/${bindir}/schooner"
    fi

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

INSANE_SKIP_${PN} += "file-rdeps"
