DESCRIPTION = "Vivotek VADP module"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

inherit gettext

PR = "ml46"

SRCREV = "06fe974df68d1316f75f29124f68fbab9086362c"

SRC_URI = "git://git@${GIT_SERVER}/analytics.git;protocol=ssh;branch=master"

S = "${WORKDIR}/git"

FIRMWARE_DIR = "/var/lib/firmware/Vivotek"

VADP_VERSION = "1.46"

SUPPORT_FILES = "${S}/VADP/package"

do_compile() {
     make
     cp ${S}/VADP/app_vencjpeg ${SUPPORT_FILES}
     cp ${S}/VADP/cgi-bin/omron_settings.cgi ${SUPPORT_FILES}/cgi-bin
     sed -i "s/%VERSION%/${VADP_VERSION}/g" ${SUPPORT_FILES}/vadp.xml
     tar czf ${VADP_MODULE_NAME} \
	    -C ${SUPPORT_FILES} \
	     cgi-bin \
	     main.sh \
             vadp.xml \
	     motion_vadp_event_0_0.xml \
	     ring_on_vadp_event.xml \
             www \
	     app_vencjpeg 
}


do_install() {
     install -d ${D}/${FIRMWARE_DIR}
     cp ${VADP_MODULE_NAME} ${D}/${FIRMWARE_DIR}
}
