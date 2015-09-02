FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

dirs755_append = " /media/card /media/extra /media/bootscript /media/mmcblk0p6 /media/mmcblk0p5 "

SRC_URI += "file://vivintbuild_gpg.pub  \
            "

do_install_append () {
    install -m 0644 ${WORKDIR}/vivintbuild_gpg.pub  ${D}${sysconfdir}/vivintbuild_gpg.pub 

    rm ${D}${sysconfdir}/issue* 
    install -m 0644 ${WORKDIR}/issue*  ${D}${sysconfdir}   
        if [ -n "${DISTRO_NAME}" ]; then
            printf "${DISTRO_NAME} " >> ${D}${sysconfdir}/issue
            printf "${DISTRO_NAME} " >> ${D}${sysconfdir}/issue.net
        fi
        if [ -n "${DISTRO_VERSION}" ]; then
            printf "${DISTRO_VERSION} " >> ${D}${sysconfdir}/issue
            printf "${DISTRO_VERSION} " >> ${D}${sysconfdir}/issue.net
        fi
        printf "\\\n \\\l\n" >> ${D}${sysconfdir}/issue
        echo >> ${D}${sysconfdir}/issue
        echo "%h"    >> ${D}${sysconfdir}/issue.net
        echo >> ${D}${sysconfdir}/issue.net
}
