set -x                                                                          
EMMCDEV=/dev/mmcblk0                                                            
SERVERIP=$(cut -f 2 -d' ' < /etc/resolv.conf)                                   
echo about to reformat $EMMCDEV ... crtl-c to abort                                     
sleep 5
/usr/bin/slimline-initemmc $EMMCDEV $SERVERIP

