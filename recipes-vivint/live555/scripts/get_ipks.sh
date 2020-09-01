#!/bin/sh

A=`grep ipk nohup.out  | grep Packaged | cut -d' ' -f6`
P=`pwd`

mkdir /tmp/tarfiles
for i in $A
do
	cp $i /tmp/tarfiles/
	E=`basename $i`
	LIB=`echo $E | grep ^lib`
	if [ -n "$LIB" ]
	then
		B=$B' '$E
	fi
done
tar  -C /tmp/tarfiles -cvzf ipk.tar.gz $B
rm /tmp/tarfiles/*
rmdir /tmp/tarfiles
