#!/bin/sh
datfile="/run/zwavetty-counter"
lockfile="$datfile.lck"
initial=0

(
    flock -x 9
    num=$initial
    if [ -e "$datfile" ]; then
        read -r num < "$datfile"
    fi

    next=`expr $num + 1`;
    echo $next > "$datfile"

    echo "$num"
) 9> "$lockfile"
exit 0
