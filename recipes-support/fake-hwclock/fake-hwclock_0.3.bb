SUMMARY = "Utility to save time on a system without a rtc"
DESCRIPTION = "If the rtc isn't battery backedup or is not available it will reset everytime power is lost. This utility fakes a hw clock by saving the clock periodically and restoring it on boot, it is only acurate to the save interval and needs NTP to be really useful"
LICENSE="GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

inherit systemd

SYSTEMD_SERVICE_${PN} = "fake-hwclock.service fake-hwclock-save.timer fake-hwclock-save.service"

RDEPENDS_${PN} = "bash"

SRC_URI = "file://fake-hwclock.sh \
           file://fake-hwclock.service \
           file://fake-hwclock-save.service \
           file://fake-hwclock-save.timer \
"

do_install () {
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/fake-hwclock.service ${D}${systemd_unitdir}/system/fake-hwclock.service
    install -m 0644 ${WORKDIR}/fake-hwclock-save.service ${D}${systemd_unitdir}/system/fake-hwclock-save.service
    install -m 0644 ${WORKDIR}/fake-hwclock-save.timer ${D}${systemd_unitdir}/system/fake-hwclock-save.timer
    install -d ${D}${systemd_unitdir}/scripts
    install -m 0755 ${WORKDIR}/fake-hwclock.sh ${D}${systemd_unitdir}/scripts/fake-hwclock.sh
}

FILES_${PN} = "${systemd_unitdir}"
