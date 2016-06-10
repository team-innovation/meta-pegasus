SUMMARY = "Vivint authored utilities catch-all package"
DESCRIPTION = "Various Vivint authored utilities for development and hw test"
SECTION = "utilities"
LICENSE = "CLOSED"
PV = "1.0.0"
PR = "r23"

PACKAGES = "${PN} ${PN}-dbg"

SRC_URI = "\
	   file://ccat \
	   file://ctail \
	   file://fccfixup \
	   file://firstboot \
	   file://firstboot-setup \
	   file://gadgetsetup \
	   file://mfr_audio_heat_test.py \
	   file://mfr_audio_test.py \
	   file://nfctest.c \
	   file://pcamtest \
	   file://resize.c \
	   file://resize.sh \
	   file://serialnumset \
	   file://touchtest \
	   file://wave_1000_hz_half_mag.wav \		
	   file://wave_1000_hz.wav \
	   file://wave_1800_hz.wav \
	   file://wave_3600_hz.wav \		
	   file://wave_750_hz.wav \
	   file://wlan-hwtest \
	   file://netm-hwtest.py \
	   file://bootsplash.rgba \
	   file://bootsplash.sh \
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
	install -m 0755 ${S}/firstboot-setup ${D}/usr/local/bin
	install -m 0755 ${S}/gadgetsetup ${D}/usr/local/bin
	install -m 0755 ${S}/resize ${D}/usr/local/bin
	install -m 0755 ${S}/nfctest ${D}/usr/local/bin
	install -m 0755 ${S}/pcamtest ${D}/usr/local/bin
	install -m 0755 ${S}/serialnumset ${D}/usr/local/bin
	install -m 0755 ${S}/fccfixup ${D}/usr/local/bin
	install -m 0755 ${S}/touchtest ${D}/usr/local/bin
	install -m 0755 ${S}/wlan-hwtest ${D}/usr/local/bin
	install -m 0755 ${S}/netm-hwtest.py ${D}/usr/local/bin
	install -m 0755 ${S}/mfr_audio_test.py ${D}/usr/local/bin
	install -m 0755 ${S}/mfr_audio_heat_test.py ${D}/usr/local/bin
	install -m 0755 ${S}/wave_750_hz.wav ${D}/usr/local/bin
	install -m 0755 ${S}/wave_1000_hz.wav ${D}/usr/local/bin
	install -m 0755 ${S}/wave_1800_hz.wav ${D}/usr/local/bin
	install -m 0755 ${S}/wave_3600_hz.wav ${D}/usr/local/bin
	install -m 0755 ${S}/wave_1000_hz_half_mag.wav ${D}/usr/local/bin

	install -d ${D}/${sysconfdir}/init.d
	install -m 0755 ${S}/firstboot ${D}/${sysconfdir}/init.d/firstboot
	update-rc.d -r ${D} firstboot start 08 S .

	install -d ${D}/${sysconfdir}/profile.d
	install -m 0755 ${S}/resize.sh ${D}/${sysconfdir}/profile.d

	install -d ${D}/home/root/

    install -d ${D}/usr/lib/images/
    install -d ${D}/media/extra/lib/images/
    install ${S}/bootsplash.rgba ${D}/usr/lib/images
    
    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${S}/bootsplash.sh  ${D}/${sysconfdir}/init.d
    update-rc.d -r ${D} bootsplash.sh start 04 S .
}

FILES_${PN}-dbg += "/usr/local/bin/.debug/"
FILES_${PN} += "/home/root /usr/local/bin/* /etc/profile.d/* \
                /usr/lib/images/* /media/extra/lib/images"
