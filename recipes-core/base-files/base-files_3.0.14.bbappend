FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

dirs755_append = " /media/card /media/extra /media/bootscript /media/mmcblk0p6 /media/mmcblk0p5 "
dirs755_remove = "/run"
volatiles_append = " run"
