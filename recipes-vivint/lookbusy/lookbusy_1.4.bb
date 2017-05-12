# Copyright (C) 2017 Vivint Innovation 

DESCRIPTION = "CPU Loading Program"
HOMEPAGE = "http://www.devin.com/lookbusy/"
SECTION = "utilities"
LIC_FILES_CHKSUM = "file://COPYING;md5=4325afd396febcb659c36b49533135d4"
LICENSE = "GPL"
PR = "r0"

SRC_URI =	"http://updateseng.vivint.com/innovation/downloads/lookbusy-${PV}.tar.gz \
		 "

S = "${WORKDIR}/lookbusy-${PV}"

do_configure() {
	ac_cv_func_malloc_0_nonnull=yes ac_cv_func_realloc_0_nonnull=yes  ./configure --host=${TARGET_SYS} --with-gnu-ld
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/lookbusy ${D}${bindir}/
}

FILES_${PN} = "${bindir}/lookbusy"

SRC_URI[md5sum] = "574398a64d52924651476dbd5b99f368"
SRC_URI[sha256sum] = "d20c04056c812a3909edc4b54a2ab35495d2cfec4524e7610792847df1fcca68"
