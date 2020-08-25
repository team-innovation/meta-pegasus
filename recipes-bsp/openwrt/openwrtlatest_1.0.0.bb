DESCRIPTION = "OpenWrt 18.06 version for MT7620 Network Module"

require openwrt.inc

GIT_ARTIFACTS_BRANCH = "${GIT_ARTIFACTS_LATEST_BRANCH}"
GIT_ARTIFACTS_SERVER ?= "${GIT_SERVER}"
GIT_ARTIFACTS_PROTOCOL ?= "ssh"

GIT_ARTIFACTS_REV ?= "${AUTOREV}"
SRCREV = "${GIT_ARTIFACTS_REV}"

PR = "r4"
PV = "${SRCPV}"

SRC_URI = "git://${GIT_ARTIFACTS_SERVER}/artifacts;protocol=${GIT_ARTIFACTS_PROTOCOL};branch=${GIT_ARTIFACTS_BRANCH} \
		  file://networkmodule"

FW_DIR = "/lib/firmware/latest"

do_install_append() {

    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/networkmodule ${D}/${sysconfdir}/init.d/
}

FILES_${PN}-mt7620 += "\
    ${sysconfdir}/init.d/networkmodule \
    "

pkg_postinst_${PN}-mt7620() {
#!/bin/sh -e
# create symlink to /lib/firmware for MT7620_* to use

if [ x"$D" = "x" ]; then
    echo "Changing directory and creating links."
    (cd /lib/firmware; ln -sf latest/* .)
else
    exit 1
fi
}
