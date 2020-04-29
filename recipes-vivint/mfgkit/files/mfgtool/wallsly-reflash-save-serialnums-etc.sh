#!/bin/bash
if [ ! -f /etc/udev/rules.d/51-imx-permissions.rules ]; then
	echo "******************************************"
	echo "Copy 51-imx-permissions.rules to /etc/udev/rules.d or run as root to have libusb permission"
	echo "******************************************"
	echo
fi

./mfgtoocli -c "Linux" -l "Wallsly-Reflash-Save-Serial-Etc"
