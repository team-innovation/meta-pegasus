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

RDEPENDS_pulseaudio-server += " \
	pulseaudio-module-rtp-send \
	pulseaudio-module-rtp-recv "
