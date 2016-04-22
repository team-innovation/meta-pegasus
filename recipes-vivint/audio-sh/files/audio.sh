#!/bin/bash
rmmods() {
	lsmod | grep -q snd_soc_imx_cx20704 &&
		rmmod snd_soc_imx_cx20704
	lsmod | grep -q snd_soc_cx20704 &&
		rmmod snd_soc_cx20704
}

initall() {
	echo 1 > /sys/class/lm48511/lm485110/access
	echo 0 > /sys/class/lm48511/lm485110/aud_reset
	echo 1 > /sys/class/lm48511/lm485110/sd_amp
	echo 1 > /sys/class/lm48511/lm485110/sd_boost
	echo 1 > /sys/class/lm48511/lm485110/fb_sel
	echo 1 > /sys/class/lm48511/lm485110/ss_ff
	echo 1 > /sys/class/lm48511/lm485110/aud_reset
}

rmmods
initall
sleep 1
modprobe snd_soc_cx20704
modprobe snd_soc_imx_cx20704
amixer sset 'I2S TX Source' 'Stream2: mic ADC'
amixer sset 'DAC Source' 'Stream3: I2S in'
grep -q sly /proc/device-tree/compatible && {
	modprobe snd_soc_gsm030x
	modprobe snd_soc_imx_gsm030x
}
