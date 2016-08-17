pkg_postinst_${PN}-server_prepend() {
        # can't do this offline
        if [ "x$D" != "x" ]; then
                exit 1
        fi

        if [ -d /home/root/.config ]
        then
                rm -rf /home/root/.config
        fi
        ln -s /.config /home/root/.config

        grep -q pulse: /etc/group || addgroup pulse
        grep -q pulse: /etc/passwd || \
            adduser --disabled-password --home=/var/run/pulse --system \
                    --ingroup pulse --no-create-home -g "Pulse audio daemon" pulse
        addgroup pulse audio
}

DEPENDS += "gdbm speex webrtc-audio-processing"

EXTRA_OECONF = "--enable-neon-opt \
		--enable-webrtc-aec \
		--disable-orc \
"

# This is fixed in upstream poky, until we update to it fix it here
RDEPENDS_pulseaudio-module-console-kit = ""
RDEPENDS_pulseaudio-module-console-kit =+ "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'consolekit', '', d)}"

RDEPENDS_pulseaudio-server += " \
        pulseaudio-module-rtp-send \
        pulseaudio-module-rtp-recv \
	pulseaudio-module-native-protocol-tcp \
	pulseaudio-module-tunnel-source \
	pulseaudio-module-tunnel-sink \
	pulseaudio-module-remap-source \
	pulseaudio-module-remap-sink \
    tcp-wrappers \
    openssl \
"
