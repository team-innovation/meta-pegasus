#!/bin/bash
export SD_BOOST=136
export FB_SEL=140
export SD_AMP=144
export SS_FF=145
export AUD_RESET=107

gpiodiroutset() {
	local gpio=$1
	local val=$2
	test -d /sys/class/gpio/gpio${gpio} || {
		echo "$1" > /sys/class/gpio/export
		echo "out" > /sys/class/gpio/gpio${gpio}/direction
	}
	echo "$val" > /sys/class/gpio/gpio${gpio}/value
}

rmmods() {
	rmmod snd_soc_imx_cx20704 || true
	rmmod snd_soc_cx20704 || true
}

initall() {
	gpiodiroutset ${SD_BOOST} 1
	gpiodiroutset ${FB_SEL} 1
	gpiodiroutset ${SD_AMP} 1
	gpiodiroutset ${SS_FF} 1
	gpiodiroutset ${AUD_RESET} 1
}

rmmods
initall
sleep 1
modprobe snd_soc_cx20704
modprobe snd_soc_imx_cx20704
amixer sset 'I2S TX Source' 'Stream2: mic ADC'
amixer sset 'DAC Source' 'Stream3: I2S in'

