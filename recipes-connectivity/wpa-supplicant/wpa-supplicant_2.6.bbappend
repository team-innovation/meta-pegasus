pkg_postinst_${PN}-updated () {
#!/bin/sh -e
# Post install to make sure we have the correct setup for updated
#

if [ x"$D" = "x" ]; then
    wpafile=/media/extra/conf/network/wpa_supplicant_wireless.conf
    if [ -f $wpafile ]; then
        grep -q -e 'disabled=1' $wpafile || sed -i "/key_mgmt=NONE/a\\\        disabled=1" $wpafile
    fi
fi
