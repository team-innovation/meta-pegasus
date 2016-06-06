FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

dirs755_append = " /media/card /media/extra /media/bootscript /media/mmcblk0p6 /media/mmcblk0p5 "

SRC_URI += "file://vivintbuild_gpg.pub  \
            "

do_install_append () {
    install -m 0644 ${WORKDIR}/vivintbuild_gpg.pub  ${D}${sysconfdir}/vivintbuild_gpg.pub 
}
