#!/bin/sh
rmmods() {
	lsmod | grep -q snd_soc_imx_cx20704 &&
		rmmod snd_soc_imx_cx20704
	lsmod | grep -q snd_soc_cx20704 &&
		rmmod snd_soc_cx20704
	lsmod | grep -q snd_soc_imx_gsm030x &&
		rmmod snd_soc_imx_gsm030x
	lsmod | grep -q snd_soc_gsm030x &&
		rmmod snd_soc_gsm030x
}

initall_withoutcx() {
	echo 1 > /sys/class/lm48511/lm485110/access
	echo 1 > /sys/class/lm48511/lm485110/sd_amp
	echo 1 > /sys/class/lm48511/lm485110/sd_boost
	echo 1 > /sys/class/lm48511/lm485110/fb_sel
	echo 1 > /sys/class/lm48511/lm485110/ss_ff
}

cx_frob() {
	v=$1
	test -f /sys/class/lm48511/lm485110/aud_reset &&
		echo $1 > /sys/class/lm48511/lm485110/aud_reset
	test -f /sys/iodbus/codec/aud_reset/value &&
		echo $1 > /sys/iodbus/codec/aud_reset/value
}

cx_reset() {
	cx_frob 0
}

cx_enable() {
	cx_frob 1
}

initall_withcx() {
	echo 1 > /sys/class/lm48511/lm485110/access
	lsmod | grep -q snd_soc_cx20704 ||
		cx_reset
	echo 1 > /sys/class/lm48511/lm485110/sd_amp
	echo 1 > /sys/class/lm48511/lm485110/sd_boost
	echo 1 > /sys/class/lm48511/lm485110/fb_sel
	echo 1 > /sys/class/lm48511/lm485110/ss_ff
	lsmod | grep -q snd_soc_cx20704 ||
		cx_enable
}

cx_dumpreg() {
	echo $1 > regread
	echo -n "$1: " >> /tmp/cxregs
	cat regread >> /tmp/cxregs
}

cx_dumpregs() {
	echo '===================' >> /tmp/cxregs
	echo regs $1 >> /tmp/cxregs
	echo '===================' >> /tmp/cxregs
	cd /sys/class/cx20704/cx20704_controls
	echo '===================' >> /tmp/cxregs
	cx_dumpreg 0x0f51
	cx_dumpreg 0x101e
	cx_dumpreg 0x116b
	cx_dumpreg 0x116d
	cx_dumpreg 0x116e
	cx_dumpreg 0x1171
	cx_dumpreg 0x1173
	cx_dumpreg 0x1176
	cx_dumpreg 0x117a
	cx_dumpreg 0x117f
	cx_dumpreg 0x1181
	cx_dumpreg 0x1184
	cx_dumpreg 0x1186
	cx_dumpreg 0x15c9
	cx_dumpreg 0x15ca
	cx_dumpreg 0x15cb
	cx_dumpreg 0x15cc
	cx_dumpreg 0x15cd
	cx_dumpreg 0x15ce
	cx_dumpreg 0x15cf
	cx_dumpreg 0x15d0
	cx_dumpreg 0x15d1
	cx_dumpreg 0x15d2
	cx_dumpreg 0x15d3
	cx_dumpreg 0x15d4
	cx_dumpreg 0x15d5
	cx_dumpreg 0x15d6
	cx_dumpreg 0x15d7
	cx_dumpreg 0x15d8
	cx_dumpreg 0x15d9
	cx_dumpreg 0x15da
	cx_dumpreg 0x15db
	cx_dumpreg 0x15dc
	cx_dumpreg 0x15e3
	cx_dumpreg 0x15e4
	cx_dumpreg 0x117b
	cx_dumpreg 0x117c
	cx_dumpreg 0x117d
	echo '===================' >> /tmp/cxregs
}

patch_lm48511_amp() {
	cd /sys/class/lm48511/lm485110
	echo 1 > access
	echo 0 > sd_boost
	echo 0 > fb_sel
}

patch_cx_aec() {
	cd /sys/class/cx20704/cx20704_controls

	echo 0x0f51 0xb0 > regwrite

	# 12 dB mic boost
	echo 0x101e 0x5 > regwrite
	# Stream 1 input rate 16/16/2
	#echo 0x116a 0xa2 > regwrite

	# Stream 2 mic input
	echo 0x116b 0x22 > regwrite
	# Stream 3 input rate 16/2 8->16k
	echo 0x116d 0xaa > regwrite
	# Stream 3 routing: DP1 In
	echo 0x116e 0x0 > regwrite

	# Stream 5 output rate 16/16
	echo 0x1171 0x20 > regwrite
	# Stream 7 output rate 16/16
	echo 0x1173 0x02 > regwrite
	# Misc routing: NO wideband AEC, 16 kHz scale out
	echo 0x1176 0x02 > regwrite
	# NO AEC + NR
	echo 0x117a 0x00 > regwrite
	# Disable Voice processing
	#echo 0x117c 0x80 > regwrite
	# Disable line in
	#echo 0x117d 0xac > regwrite

	# DACin source
	echo 0x117f 0x03 > regwrite
	# I2S1 source
	echo 0x1181 0x02 > regwrite
	# mixin0 source
	echo 0x1184 0x00 > regwrite
	# mixin2 source
	echo 0x1186 0x00 > regwrite

	# AEC/NLP stuff from patch
	echo 0x15c9 0x6b > regwrite
	echo 0x15ca 0x01 > regwrite
	echo 0x15cb 0x89 > regwrite
	echo 0x15cc 0xa3 > regwrite
	echo 0x15cd 0xd1 > regwrite
	echo 0x15ce 0x15 > regwrite
	echo 0x15cf 0xdb > regwrite
	echo 0x15d0 0x60 > regwrite
	echo 0x15d1 0xcb > regwrite
	echo 0x15d2 0x6b > regwrite
	echo 0x15d3 0x05 > regwrite
	echo 0x15d4 0xdb > regwrite
	echo 0x15d5 0x8f > regwrite
	echo 0x15d6 0x87 > regwrite
	echo 0x15d7 0x03 > regwrite
	echo 0x15d8 0x4c > regwrite
	echo 0x15d9 0x8c > regwrite
	echo 0x15da 0xa3 > regwrite
	echo 0x15db 0x4c > regwrite
	echo 0x15dc 0x93 > regwrite
	echo 0x15e3 0xa9 > regwrite
	echo 0x15e4 0x83 > regwrite

	echo 0x117b 0x00 > regwrite
	echo 0x117c 0x00 > regwrite
	echo 0x117d 0xad > regwrite

	echo 1 > newc
}

init_wallsly() {
	initall_withoutcx
	sleep 1
	echo 0 > /sys/iodbus/codec/aud_lvl_x_oe/value
	patch_lm48511_amp
}

init_slimline() {
	initall_withcx
	sleep 1
	modprobe snd_soc_cx20704
	modprobe snd_soc_imx_cx20704
	amixer sset 'I2S TX Source' 'Stream2: mic ADC'
	amixer sset 'DAC Source' 'Stream3: I2S in'
	sleep 1
	cx_dumpregs before
	patch_cx_aec
	cx_dumpregs after
	patch_lm48511_amp
	modprobe snd_soc_gsm030x
	modprobe snd_soc_imx_gsm030x
}


rmmods

platform=$(strings /proc/device-tree/compatible |
        grep vivint |
        sed s/^vivint,//)
	echo $platform " reported to audio.sh"

if [ "$platform" == "wallsly" ] || [ "$platform" == "brazen" ]; then
	init_wallsly
elif [ "$platform" == "sly" ]; then
	init_slimline
else
#slimline
	init_slimline
fi
