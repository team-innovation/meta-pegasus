#!/bin/bash
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

LED_SYS="/sys/bus/i2c/devices/3-0034/leds"

led_slow() {
	echo 50 100 0 > ${LED_SYS}/l_primary_levels
	echo 5 > ${LED_SYS}/l_primary_blink_off_time
	echo 10 > ${LED_SYS}/l_primary_blink_on_time
	echo 2 > ${LED_SYS}/l_primary_led
}

led_medium() {
	echo 3 > ${LED_SYS}/l_primary_blink_off_time
	echo 6 > ${LED_SYS}/l_primary_blink_on_time

}

led_fast() {
	echo 1 > ${LED_SYS}/l_primary_blink_off_time
	echo 3 > ${LED_SYS}/l_primary_blink_on_time
}

read_green() {
	cat ${LED_SYS}/l_primary_levels | cut -d ' ' -f 1
}
read_red() {
	cat ${LED_SYS}/l_primary_levels | cut -d ' ' -f 2
}
read_blue() {
	cat ${LED_SYS}/l_primary_levels | cut -d ' ' -f 3
}

led_green_up() {
	green=$(read_green)
	red=$(read_red)
	blue=$(read_blue)
	((green+=10))
	echo $green $red $blue > ${LED_SYS}/l_primary_levels
	cat ${LED_SYS}/l_primary_levels
}

led_green_down() {
	green=$(read_green)
	red=$(read_red)
	blue=$(read_blue)
	((green-=10))
	echo $green $red $blue > ${LED_SYS}/l_primary_levels
	cat ${LED_SYS}/l_primary_levels
}


led_red_up() {
	green=$(read_green)
	red=$(read_red)
	blue=$(read_blue)
	((red+=10))
	echo $green $red $blue > ${LED_SYS}/l_primary_levels
	cat ${LED_SYS}/l_primary_levels
}

led_red_down() {
	green=$(read_green)
	red=$(read_red)
	blue=$(read_blue)
	((red-=10))
	echo $green $red $blue > ${LED_SYS}/l_primary_levels
	cat ${LED_SYS}/l_primary_levels
}


led_blue_up() {
	green=$(read_green)
	red=$(read_red)
	blue=$(read_blue)
	((blue+=10))
	echo $green $red $blue > ${LED_SYS}/l_primary_levels
	cat ${LED_SYS}/l_primary_levels
}

led_blue_down() {
	green=$(read_green)
	red=$(read_red)
	blue=$(read_blue)
	((blue-=10))
	echo $green $red $blue > ${LED_SYS}/l_primary_levels
	cat ${LED_SYS}/l_primary_levels
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
	led_slow)
		led_slow
		;;
	led_medium)
		led_medium
		;;
	led_fast)
		led_fast
		;;
	led_red_up)
		led_red_up
		;;
	led_green_up)
		led_green_up
		;;
	led_blue_up)
		led_blue_up
		;;
	led_red_down)
		led_red_down
		;;
	led_green_down)
		led_green_down
		;;
	led_blue_down)
		led_blue_down
		;;
esac
