SUMMARY = "Vivint authored utilities catch-all package"
DESCRIPTION = "Various Vivint authored utilities for development and hw test"
SECTION = "utilities"
LICENSE = "CLOSED"
PV = "1.0.0"
PR = "r71"

PACKAGES = "${PN} ${PN}-dbg"

RDEPENDS_${PN} = " \
    python3-pysodium \
    libpulse-simple \
    libpulse \
    libasound \
"

SRC_URI = "\
	   file://ccat \
	   file://ctail \
	   file://fcc_active_receive \
	   file://fccfixup \
	   file://labmodem \
	   file://firstboot \
	   file://firstboot-setup \
	   file://gadgetsetup \
	   file://bootgadgets.sh \
	   file://mfr_audio_heat_test.py \
	   file://mfr_audio_test.py \
	   file://nfctest.c \
	   file://pcamtest \
	   file://resize.c \
	   file://resize.sh \
	   file://serialnumset \
	   file://set-u-boot-part \
	   file://sierra_get_version.py \
	   file://simplesuspend \
	   file://slimline-update \
	   file://sly-update \
	   file://pa_info \
	   file://touchtest \
	   file://wave_1000_hz_half_mag.wav \
	   file://dtmf-sharp.wav \
	   file://dtmf-one.wav \
	   file://dtmf-two.wav \
	   file://dtmf-three.wav \
	   file://dtmf-nine.wav \
	   file://wlan-hwtest \
	   file://netm-hwtest.py \
	   file://zwave-hwtest.py \
	   file://genkeys \
	   file://clips.sh \
	   file://lockdown_panel \
	   file://user_battery_test.py \
	   file://latest-sly-sprint-build \
	   file://wallslyscreentest \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"
S = "${WORKDIR}"

do_compile() {
	${CC} resize.c -o resize
	${CC} nfctest.c -o nfctest
}

do_install() {
	install -d ${D}/usr/local/bin
	install -m 0755 ${S}/ctail ${D}/usr/local/bin
	install -m 0755 ${S}/ccat ${D}/usr/local/bin
	install -m 0755 ${S}/fcc_active_receive ${D}/usr/local/bin
	install -m 0755 ${S}/firstboot-setup ${D}/usr/local/bin
	install -m 0755 ${S}/gadgetsetup ${D}/usr/local/bin
	install -m 0755 ${S}/resize ${D}/usr/local/bin
	install -m 0755 ${S}/nfctest ${D}/usr/local/bin
	install -m 0755 ${S}/pcamtest ${D}/usr/local/bin
	install -m 0755 ${S}/serialnumset ${D}/usr/local/bin
	install -m 0755 ${S}/set-u-boot-part ${D}/usr/local/bin
	install -m 0755 ${S}/sierra_get_version ${D}/usr/local/bin
	install -m 0755 ${S}/simplesuspend ${D}/usr/local/bin
	install -m 0755 ${S}/slimline-update ${D}/usr/local/bin
	install -m 0755 ${S}/sly-update ${D}/usr/local/bin
	install -m 0755 ${S}/pa_info ${D}/usr/local/bin
	install -m 0755 ${S}/fccfixup ${D}/usr/local/bin
	install -m 0755 ${S}/labmodem ${D}/usr/local/bin
	install -m 0755 ${S}/touchtest ${D}/usr/local/bin
	install -m 0755 ${S}/wlan-hwtest ${D}/usr/local/bin
	install -m 0755 ${S}/netm-hwtest.py ${D}/usr/local/bin
	install -m 0755 ${S}/zwave-hwtest.py ${D}/usr/local/bin
	install -m 0755 ${S}/mfr_audio_test.py ${D}/usr/local/bin
	install -m 0755 ${S}/mfr_audio_heat_test.py ${D}/usr/local/bin
	install -m 0644 ${S}/wave_1000_hz_half_mag.wav ${D}/usr/local/bin
	install -m 0644 ${S}/dtmf-sharp.wav ${D}/usr/local/bin
	install -m 0644 ${S}/dtmf-one.wav ${D}/usr/local/bin
	install -m 0644 ${S}/dtmf-two.wav ${D}/usr/local/bin
	install -m 0644 ${S}/dtmf-three.wav ${D}/usr/local/bin
	install -m 0644 ${S}/dtmf-nine.wav ${D}/usr/local/bin
	install -m 0755 ${S}/genkeys ${D}/usr/local/bin
	install -m 0755 ${S}/lockdown_panel ${D}/usr/local/bin
	install -m 0755 ${S}/user_battery_test.py ${D}/usr/local/bin
	install -m 0755 ${S}/latest-sly-sprint-build ${D}/usr/local/bin
	install -m 0755 ${S}/wallslyscreentest ${D}/usr/local/bin

	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${S}/firstboot ${D}/${sysconfdir}/init.d/firstboot
	update-rc.d -r ${D} firstboot start 04 S .

	install -m 0755 ${S}/clips.sh ${D}/${sysconfdir}/init.d/
	update-rc.d -r ${D} clips.sh start 34 S .

	install -m 0755 ${S}/bootgadgets.sh ${D}/${sysconfdir}/init.d/bootgadgets.sh
	update-rc.d -r ${D} bootgadgets.sh start 34 S .

	install -d ${D}/${sysconfdir}/profile.d
	install -m 0755 ${S}/resize.sh ${D}/${sysconfdir}/profile.d

	install -d ${D}/home/root/
}

FILES_${PN}-dbg += "/usr/local/bin/.debug/"
FILES_${PN} += "/home/root /usr/local/bin/* /etc/profile.d/*"


pkg_postinst_${PN} () {
#!/bin/sh
if [ "x$D" != "x" ]; then
        exit 1
fi

# Overwrite existing files.
if grep -q wallsly /proc/device-tree/compatible; then
	rm /usr/local/bin/mfr_audio_test.py 
	cp /usr/local/bin/mfr-audio-test-wallsly    /usr/local/bin/mfr-audio-test
	cp /usr/local/bin/vaudio-wallsly    /usr/local/bin/vaudio
fi

# Maintain compatiblity with old locations:
ln -sf /opt/2gig/iod/scripts/nfctest-sly /usr/local/bin/
ln -sf /opt/2gig/iod/scripts/nfcslytest /usr/local/bin/
ln -sf /opt/2gig/iod/scripts/nfccmd /usr/local/bin/
}
