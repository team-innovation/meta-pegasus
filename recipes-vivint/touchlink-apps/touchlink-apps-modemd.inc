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
#!/bin/bash -e
# Post install to flash the modem to default carrier on WallSly
#
if [ x"$D" = "x" ]
then
    CLASS="/sys/class/cell/cell0"
    # look for Sierra cell class on WallSly
    if [ -d "$CLASS" ]
    then
        # get the AT&T image filename (contains ".A.")
        # when we switch to Verizon look for ".V."
        IMAGE_FILE="$(ls /var/lib/firmware/Sierra/RHL*.A.*)"
        if [ -z $IMAGE_FILE ]; then
            echo "ERROR: AT&T firmware image not found in /var/lib/firmware/Sierra"
            exit 1
        fi

        # Check to see if the modem is already turned on, if so we don't have to cycle it
        echo 1 > $CLASS/access
        MODEM_ENABLED=$(cat $CLASS/enable)
        if [ $MODEM_ENABLED = "0" ]
        then
            # turn modem on
            echo "Turning modem on..."
            echo 0 > $CLASS/enable
            echo 1 > $CLASS/enable
            sleep 5
        fi

        echo "Flashing Sierra HL7588 modem with: "$IMAGE_FILE
        swdltool $IMAGE_FILE &

        # sleep to let the image file load, then reset to begin flashing
        sleep 2
        echo 0 > $CLASS/reset
        sleep 2
        echo 1 > $CLASS/reset

        # wait until flash is done
        end=$((SECONDS+60))
        while [ $SECONDS -lt $end ]; do
            if pgrep -x "swdltool" > /dev/null
            then
                # flash program still running - wait before checking again
                sleep 1
            else
                echo "Sierra HL7588 flash programming completed"
                exit 0
            fi
        done
        echo "Timed out flashing Sierra HL7588 modem"
        exit 1
    else
        echo "Sierra modem class not found"
        exit 1
    fi
else
    echo "Not flashing modem"
    exit 1
fi
}

FILES_${PN}-modemd = "\
	${INSTALL_DIR}/modemd/*	\
	${sysconfdir}/init.d/modemd		\
	${sysconfdir}/procman.d/modemd		\
	"


