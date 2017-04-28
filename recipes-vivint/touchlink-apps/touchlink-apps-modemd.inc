###
### Change MODEMD_PREPEND everytime you touch this script
###
MODEMD_PREPEND = "-4"

MODEMD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-modemd = "${PR}${MODEMD_PREPEND}${MODEMD_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-modemd"
INITSCRIPT_NAME_${PN}-modemd = "modemd"
INITSCRIPT_PARAMS_${PN}-modemd = "start 26 2 3 4 5 ."

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/modemd/
	${RSYNC_CMD} ${S}/code/modemd/ ${D}/${INSTALL_DIR}/modemd/
	chmod +x ${D}/${INSTALL_DIR}/modemd/modemd.pyo
}

pkg_postinst_${PN}-modemd () {
#!/bin/sh -e
# Post install to flash the modem to default carrier on WallSly
#
if [ x"$D" = "x" ]; then
    if [ -f /proc/device-tree/compatible ]; then
        exit 0
    fi

    # look for Sierra cell class on WallSly
    if [ -d "/sys/class/cell/cell0" ]; then
        # get the AT&T image filename (contains ".A.")
        # when we switch to Verizon look for ".V."
        IMAGE_FILE="$(ls /var/lib/firmware/Sierra/RHL*.A.*)"
        if [ -z $IMAGE_FILE ]; then
            exit 0
        fi

        echo 1 > /sys/class/cell/cell0/access
        echo 0 > /sys/class/cell/cell0/enable
        echo 1 > /sys/class/cell/cell0/enable
        echo 1 > /sys/class/cell/cell0/reset
        swdltool $IMAGE_FILE &
        sleep 5
        echo 1 > /sys/class/cell/cell0/onoff
        fg 1
    fi
else
    exit 1
fi
}

FILES_${PN}-modemd = "\
	${INSTALL_DIR}/modemd/*	\
	${sysconfdir}/init.d/modemd		\
	${sysconfdir}/procman.d/modemd		\
	"


