DESCRIPTION = "Vivotek VADP module"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

inherit gettext

PR = "ml47"

SRCREV = "06fe974df68"

SRC_URI = "git://git@${GIT_SERVER}/analytics.git;protocol=ssh"

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
