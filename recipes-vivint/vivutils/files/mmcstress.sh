#/bin/bash

ITERI=5
ITERJ=20

TOTALCLIPS=0

LIFEMATCHEXIT=0x01
TESTDIR=/media/extra/mmcstresstestdir

prep() {
	mkdir -p $TESTDIR
	cd $TESTDIR
        dd if=/dev/urandom of=source.bin bs=22M count=1
}

bench() {
        df -h .
	cd $TESTDIR
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
	cd $TESTDIR
        rm -f source.bin
        rm -f dest_*.bin
}

while true
do
        prep
        time bench
        cleanup
        date
        echo TOTALCLIPS $TOTALCLIPS so far
        mmc extcsd read /dev/mmcblk0 \
		| grep LIFE_TIME.*_B
        mmc extcsd read /dev/mmcblk0 \
		| grep LIFE_TIME.*_B \
		| awk '{print $NF}' \
		| grep $LIFEMATCHEXIT && exit 0
done
exit 0

