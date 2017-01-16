DESCRIPTION = "Pulseaudio Meta package w/ initscript et. al."
SECTION = "audio"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=be94729c3d0e226497bf9ba8c384e96f"
PR = "r8"

RDEPENDS_${PN} = "\
  pulseaudio-module-alsa-sink \
  pulseaudio-module-alsa-source \
  pulseaudio-module-cli \
  pulseaudio-module-cli-protocol-tcp \
  pulseaudio-module-cli-protocol-unix \
  pulseaudio-module-combine \
  pulseaudio-module-default-device-restore \
  pulseaudio-module-detect\
  pulseaudio-module-echo-cancel \
  pulseaudio-module-loopback \
  pulseaudio-module-native-protocol-unix \
  pulseaudio-module-null-sink \
  pulseaudio-module-pipe-sink \
  pulseaudio-module-pipe-source \
  pulseaudio-module-remap-source \
  pulseaudio-module-rescue-streams\
  pulseaudio-module-simple-protocol-tcp \
  pulseaudio-module-suspend-on-idle \
  pulseaudio-module-volume-restore \
  pulseaudio-server \
"

inherit update-rc.d

export TARGET_PFPU = "${TARGET_FPU}"
INITSCRIPT_NAME = "pulseaudio"
INITSCRIPT_PARAMS = "defaults 23"

SRC_URI = "\
  file://COPYING \
  file://pulseaudio \
  file://session \
  file://asound.conf \
  file://procman.d \
  file://daemon.conf \
"

do_install() {
    install -d ${D}/${sysconfdir}/init.d
    install -d ${D}/${sysconfdir}/procman.d
    install -m 0755 ${WORKDIR}/pulseaudio ${D}/${sysconfdir}/init.d/
    install -d ${D}/${sysconfdir}/pulse
    install -m 0755 ${WORKDIR}/session ${D}/${sysconfdir}/pulse/session.pulseaudio-meta-sly
    install -m 0644 ${WORKDIR}/asound.conf ${D}/${sysconfdir}/pulse/asound.conf.pulseaudio-meta-sly
    install -m 0644 ${WORKDIR}/daemon.conf ${D}/${sysconfdir}/pulse/daemon.conf.pulseaudio-meta-sly

    if [ "x${TARGET_PFPU}" = "xsoft" ] ; then
         sed -i -e s:resample-method=sinc-fastest:resample-method=trivial: ${D}${sysconfdir}/init.d/pulseaudio
    fi
    install -m 755 ${WORKDIR}/procman.d/* ${D}/${sysconfdir}/procman.d
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

pkg_postinst_${PN} () {
#!/bin/sh
if [ "x$D" != "x" ]; then
        exit 1
fi

if grep audio /etc/group; then
	grep pulse /etc/group || addgroup pulse
fi

# Overwrite existing configfiles, yuck!
cp /etc/pulse/session.pulseaudio-meta-sly /etc/pulse/session
cp /etc/pulse/asound.conf.pulseaudio-meta-sly /etc/pulse/asound.conf
cp /etc/pulse/daemon.conf.pulseaudio-meta-sly /etc/pulse/daemon.conf
}


CONFFILES_${PN} = "\
  ${sysconfdir}/init.d/pulseaudio \
  ${sysconfdir}/pulse/session.pulseaudio-meta-sly \
  ${sysconfdir}/pulse/asound.conf.pulseaudio-meta-sly \
  ${sysconfdir}/pulse/daemon.conf.pulseaudio-meta-sly \
"

# At the time the postinst runs, dbus might not be setup so only restart if running
pkg_postinst_hal () {
        # can't do this offline
        if [ "x$D" != "x" ]; then
                exit 1
        fi

        grep haldaemon ${sysconfdir}/group || addgroup haldaemon
        grep haldaemon ${sysconfdir}/passwd || adduser --disabled-password --system --home /var/run/hald --no-create-home haldaemon --ingroup haldaemon -g HAL

        ${sysconfdir}/init.d/populate-volatile.sh update

        DBUSPID=`pidof dbus-daemon`

        if [ "x$DBUSPID" != "x" ]; then
                ${sysconfdir}/init.d/dbus-1 force-reload
        fi
}

pkg_postrm_hal () {
        deluser haldaemon || true
        delgroup haldaemon || true
}

