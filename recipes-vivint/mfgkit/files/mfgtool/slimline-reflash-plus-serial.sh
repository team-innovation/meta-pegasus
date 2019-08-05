#!/bin/bash
if [ ! -f /etc/udev/rules.d/99-imx-permissions.rules ]; then
	echo "******************************************"
	echo "Copy 99-imx-permissions.rules to /etc/udev/rules.d or run as root to have libusb permission"
	echo "******************************************"
	echo
fi

./mfgtoolcli -c "Linux" -l "Slimline-Reflash-Plus-Serial"
