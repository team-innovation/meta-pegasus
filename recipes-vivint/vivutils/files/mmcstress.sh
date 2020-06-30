#/bin/bash
DRYRUN="FALSE"
test "$1" = "dryrun" && export DRYRUN="TRUE"
mmc -h >/dev/null 2>&1 || export DRYRUN="TRUE"

if [ "$DRYRUN" = "TRUE" ]; then
	export DRYRUNPROPS=/tmp/fakeprops
cat > $DRYRUNPROPS << __END__
eMMC Life Time Estimation A [EXT_CSD_DEVICE_LIFE_TIME_EST_TYP_A]: 0x05
eMMC Life Time Estimation B [EXT_CSD_DEVICE_LIFE_TIME_EST_TYP_B]: 0x00
eMMC Pre EOL information [EXT_CSD_PRE_EOL_INFO]: 0x01
__END__
	catprops() {
		cat $DRYRUNPROPS
	}
else
	catprops() {
		mmc extcsd read /dev/mmcblk0
	}
fi

# set this to something else other than 0 if continuing a previous test
# and you want totalclips instead of just clips for this run
TOTALCLIPS=0
#TOTALCLIPS=5400

getprop() {
	local match=$1
	catprops | grep $match | awk '{print $NF}'
}

get_life_a() {
	getprop EXT_CSD_DEVICE_LIFE_TIME_EST_TYP_A
}

get_life_b() {
	getprop EXT_CSD_DEVICE_LIFE_TIME_EST_TYP_B
}

get_eol_life() {
	getprop EXT_CSD_PRE_EOL_INFO
}

init_life_vars() {
	INITIAL_LIFE_A=$(get_life_a)
	INITIAL_LIFE_B=$(get_life_b)
	INITIAL_EOL_LIFE=$(get_eol_life)
}


announce_start() {
	echo "starting stress test"
	echo "Initial LIFE_A is $INITIAL_LIFE_A"
	echo "Initial LIFE_B is $INITIAL_LIFE_B"
	echo "Initial EOL_LIFE is $INITIAL_EOL_LIFE"
}

check_life_var_changed() {
	test $INITIAL_LIFE_A != $(get_life_a) &&
		echo "LIFE_A has changed" &&
		return 0
	test $INITIAL_LIFE_B != $(get_life_b) &&
		echo "LIFE_B has changed" &&
		return 0
	test $INITIAL_EOL_LIFE != $(get_eol_life) &&
		echo "INITIAL_EOL_LIFE has changed" &&
		return 0
	return 1
}

announce_done() {
	echo "stress test finished"
	echo "final LIFE_A is $(get_life_a)"
	echo "final LIFE_B is $(get_life_b)"
	echo "final EOL_LIFE is $(get_eol_life)"
}

TESTDIR=/media/extra/mmcstresstestdir

benchprep() {
	test "$DRYRUN" = "TRUE"  && return
	mkdir -p $TESTDIR
	cd $TESTDIR
	dd if=/dev/urandom of=source.bin bs=22M count=1
}

bench() {
	local ITERI=5
	local ITERJ=20
	test "$DRYRUN" = "TRUE"  && sleep 5 && return
	cd $TESTDIR
	df -h .
	for i in $(seq $ITERI)
	do
		for j in $(seq $ITERJ)
		do
			cp source.bin dest_${i}${j}.bin
			let TOTALCLIPS++
			echo -n .
		done
	done
	echo
	sync
	df -h .
}

cleanup() {
	test "$DRYRUN" = "TRUE"  && sleep 5 && return
	cd $TESTDIR
	rm -f source.bin
	rm -f dest_*.bin
}

main() {
	init_life_vars
	announce_start
	cleanup
	while true
	do
		benchprep
		time bench
		cleanup
		date
		echo TOTALCLIPS $TOTALCLIPS so far
		check_life_var_changed &&
			announce_done &&
			exit 0
	done
exit 0
}

main
