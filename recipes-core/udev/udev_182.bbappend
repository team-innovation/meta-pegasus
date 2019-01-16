# Vivint extra configuration udev scripts and rules
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "r11"

SRC_URI_append = " file://mount.sh file://zwavettyid.sh file://local.rules file://mount.blacklist"

do_install_append () {
    if [ -e "${WORKDIR}/mount.sh" ]; then
        install -d ${D}${sysconfdir}/udev/scripts
        install -m 0755 ${WORKDIR}/mount.sh ${D}${sysconfdir}/udev/scripts/mount.sh
    fi
    if [ -e "${WORKDIR}/zwavettyid.sh" ]; then
        install -d ${D}${sysconfdir}/udev/scripts
        install -m 0755 ${WORKDIR}/zwavettyid.sh ${D}${sysconfdir}/udev/scripts/zwavettyid.sh
    fi
    if [ -e "${WORKDIR}/local.rules" ]; then
        install -d ${D}${sysconfdir}/udev/rules.d
		if [ -e ${D}${sysconfdir}/udev/rules.d/local.rules ]; then
			rm ${D}${sysconfdir}/udev/rules.d/local.rules
		fi
        install -m 0644 ${WORKDIR}/local.rules ${D}${sysconfdir}/udev/rules.d/local.rules
    fi
    if [ -e "${WORKDIR}/mount.blacklist" ]; then
        install -d ${D}${sysconfdir}/udev
        install -m 0644 ${WORKDIR}/mount.blacklist ${D}${sysconfdir}/udev/mount.blacklist
    fi

    sed -i 's/--timeout=3/--timeout=10/' ${D}${sysconfdir}/init.d/udev
}
