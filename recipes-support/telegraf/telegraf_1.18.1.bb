SUMMARY = "golang-based metrics collecting and reporting; awesomesauce"
HOMEPAGE = "https://github.com/influxdata/telegraf"
SECTION = "console/network"
LICENSE = "CLOSED"

PR = "r1"

SRC_URI = "file://bin/telegraf \
           file://bin/telegraf_arm64 \
           file://bin/logz \
           file://bin/metricz \
           file://procman.d/logz \
           file://procman.d/metricz \
           file://logz.conf \
           file://metricz.conf \
	"


INSANE_SKIP_${PN} += "already-stripped"

do_install() {
    install -d ${D}/${bindir}

    if [ ${HOST_SYS} != "aarch64-poky-linux" ]; then
	install -m 0755 "${WORKDIR}/bin/telegraf" "${D}/${bindir}"
    else
	install -m 0755 "${WORKDIR}/bin/telegraf_arm64" "${D}/${bindir}/telegraf"
    fi

    install -d ${D}/${sysconfdir}/telegraf
    install -m 0755 ${WORKDIR}/*.conf ${D}${sysconfdir}/telegraf

    install -d ${D}/${sysconfdir}/telegraf/procman.d
    install -m 0755 ${WORKDIR}/procman.d/metricz ${D}${sysconfdir}/telegraf/procman.d
    install -m 0755 ${WORKDIR}/procman.d/logz ${D}${sysconfdir}/telegraf/procman.d

    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/bin/logz ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/bin/metricz ${D}${sysconfdir}/init.d
}

FILES_${PN} = "\
  ${bindir}/telegraf \
  ${sysconfdir}/telegraf \
  ${sysconfdir}/init.d/logz \
  ${sysconfdir}/init.d/metricz \
  ${sysconfdir}/procman.d/metricz \
  ${sysconfdir}/procman.d/logz \
"

