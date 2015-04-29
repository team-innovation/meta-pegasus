#!/bin/sh
export SD_BOOST=136
export FB_SEL=140
export SD_AMP=144
export SS_FF=145
export AUD_RESET=107
export SILENCEDEV=/dev/zero
export RANDOMDEV=/dev/urandom
export DATADEV=/tmp/datadev

gpiodiroutset() {
	local gpio=$1
	local val=$2
	test -d /sys/class/gpio/gpio${gpio} || {
		echo "$1" > /sys/class/gpio/export
		echo "out" > /sys/class/gpio/gpio${gpio}/direction
	}
	echo "$val" > /sys/class/gpio/gpio${gpio}/value
}

gpioset() {
	local gpio=$1
	local val=$2
	echo "$val" > /sys/class/gpio/gpio${gpio}/value
}

initall() {
	gpiodiroutset ${SD_BOOST} 1
	gpiodiroutset ${FB_SEL} 1
	gpiodiroutset ${SD_AMP} 1
	gpiodiroutset ${SS_FF} 1
	gpiodiroutset ${AUD_RESET} 1
	test -L ${DATADEV} ||
		ln -sf ${SILENCEDEV} ${DATADEV}
}

rmmods() {
	rmmod snd_soc_imx_cx20704 || true
	rmmod snd_soc_cx20704 || true
}

startplay() {
	killall -9 aplay
	rmmods
	initall
	modprobe snd_soc_cx20704
	modprobe snd_soc_imx_cx20704
	amixer sset 'I2S TX Source' 'Stream2'
	amixer sset 'DAC Source' 'Stream3'
#	amixer sset 'Playback Gain' '50%'
	(nohup aplay -f cd ${DATADEV} > /dev/null) &
}

stopplay() {
	killall -9 aplay
	rmmods
}

case "$1" in
	startplay)
		startplay
		;;
	sd_boost)
		gpioset ${SD_BOOST} $2
		;;
	fb_sel)
		gpioset ${FB_SEL} $2
		;;
	sd_amp)
		gpioset ${SD_AMP} $2
		;;
	ss_ff)
		gpioset ${SS_FF} $2
		;;
	random)
		ln -sf ${RANDOMDEV} ${DATADEV}
		;;
	silence)
		ln -sf ${SILENCEDEV} ${DATADEV}
		;;
	stop)
		stopplay
		;;
esac
