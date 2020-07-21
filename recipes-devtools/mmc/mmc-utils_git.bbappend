LIC_FILES_CHKSUM = "file://mmc.c;beginline=1;endline=17;md5=f8b01ca0e7a359872d513a2d0bfd0d28"
SRCREV = "73d6c59af8d1bcedf5de4aa1f5d5b7f765f545f5"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PV = "0.2"

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/cjb/mmc-utils.git;protocol=https;branch=master \
	file://0001-mmc-utils-treat-FIRMWARE_VERSION-as-binary-field-ins.patch"
