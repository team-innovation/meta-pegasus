### NOTE ###
# When adding a new package to this recipe, create an inc file then add the require line
# in the inc file, you can have any prepend and append function, init script and postinst
# do_install_append is use to copy all the files to it right location for packaging
# last thing is to add the new package name to the PACKAGES = in this file.
####

DESCRIPTION = "2gig Touchlink Apps"
LICENSE = "CLOSED"
require touchlink-apps-audmgrd.inc
require touchlink-apps-sundance.inc

require touchlink-apps-rtspd.inc
require touchlink-apps-videod.inc

require touchlink-apps-launcherd.inc
require touchlink-apps-bootsplash.inc

# fcc test apps
require touchlink-apps-test-ui.inc
require touchlink-apps-test-daemon.inc

require touchlink-apps-pcamd.inc
require touchlink-apps-iod.inc
require touchlink-apps-345d.inc
require touchlink-apps-rf915d.inc
require touchlink-apps-cloudd.inc
require touchlink-apps-modemd.inc
require touchlink-apps-multiplexerd.inc

# adtd is touchlink dependent
#require touchlink-apps-adtd.inc

require touchlink-apps-updated.inc
require touchlink-apps-usage-analytics.inc
require touchlink-apps-zwaved.inc
require touchlink-apps-netd.inc
require touchlink-apps-utils.inc
require touchlink-apps-httpd.inc
require touchlink-apps-ssdpd.inc
require touchlink-apps-webd.inc
require touchlink-apps-dbapd.inc
require touchlink-apps-procmand.inc
require touchlink-apps-favicon.inc
require touchlink-apps-global-conf.inc
require touchlink-apps-mmpd.inc
require touchlink-apps-smarthomed.inc
require touchlink-apps-listenerd.inc

#roubaix
require touchlink-apps-roubaix-services.inc
require touchlink-apps-initpumpernickel.inc

# framework
require touchlink-apps-framework.inc
require touchlink-apps-qml-framework.inc
require touchlink-apps-qml-framework-images.inc    

# Sound stuffs

require touchlink-apps-sounds-wav-abs.inc  
require touchlink-apps-sounds-wav-beeps.inc
require touchlink-apps-sounds-wav-chimes.inc
require touchlink-apps-sounds-wav-pauses.inc
require touchlink-apps-sounds-wav-ad.inc   
require touchlink-apps-sounds-wav-dtmf.inc   
require touchlink-apps-sounds-wav-vocab.inc

# pyftpd daemon
require touchlink-apps-pyftpd.inc

S = "${WORKDIR}"

DISTRO_PR = ".1"

PR = "ml108"
PV = "1.0.0+git${SRCPV}"

SRCREV = "${GIT_APPS_REV}"
SRCBRANCH = "${GIT_APPS_BRANCH}"

GIT_APPS_SERVER ?= "${GIT_SERVER}"
GIT_APPS_PROTOCOL ?= "ssh"

SRC_URI = "git://${GIT_APPS_SERVER}/${GIT_APPS_TAG};protocol=${GIT_APPS_PROTOCOL};branch=${SRCBRANCH} \
    file://procman.d \
    file://procman.d-fcc \
    file://favicon.ico \
    "

S = "${WORKDIR}/git"

PYTHON_BASEVERSION = "3.3"
PREFERRED_VERSION_python3 = "3.3.3"
PREFERRED_VERSION_python-native = "3.3.3"

inherit autotools update-rc.d python-dir pythonnative

RSYNC_CMD = "rsync -azv --exclude=.svn --exclude=test --exclude=.coverage --exclude=_coverage --exclude=_user_conf"

FRAMEWORK_DIR = "${PYTHON_SITEPACKAGES_DIR}"
INSTALL_DIR = "/opt/2gig"


#DEPENDS_REMOVED += "gst-rtsp-server # no clue

DEPENDS = " \
	gst-plugins-bad \
	gst-plugins-base \
	gst-plugins-good \
	gst-plugins-ugly \
	gstreamer \
	python3-bcrypt-native \
	python3-cachetools \
	python3-cherrypy-native \
	python3-dateutil-native \
	python3-gnupg-native \
	python3-intelhex-native \
	python3-jinja2-native \
	python3-markupsafe-native \
	python3-mixpanel-native \
	python3-mock-native \
	python3-msgpack-native \
	python3-native \
	python3-pexpect-native \
	python3-ptyprocess-native \
	python3-psutil-native \
	python3-pyalsaaudio-native \
	python3-pycrypto-native \
	python3-pyftpdlib-native \
	python3-pyinotify-native \
	python3-pyqt5-native \
	python3-pyserial-native \
	python3-pytz-native \
	python3-requests-native \
	python3-six-native \
	python3-setproctitle-native \
	python3-soco-native \
	python3-sparsedict-native \
	python3-phue-native \
"


RDEPENDS_${PN} = "\
	python3-intelhex \
	python3-subprocess \
	python3-pyserial \
	python3-pyalsaaudio \
	python3-pytz \
	python3-threading \
	python3-setproctitle \
	python3-soco \
        python3-jsonschema \
	python3-brisa \
	python3-sparsedict \
	python3-phue \
"

do_compile() {

	# generate proxies
	if [ ${BUILD_BOT_BUILD} ] ; then
			bbnote "This is a buildbot build"
			if [ -e ${HOME}/CodeBitBucket/strings/string_table.py ]; then
				mkdir -p ${S}/code/sundance/proxies/cloud/sundance_proxies
				mkdir -p ${S}/code/sundance/proxies/python/sundance_proxies
				cp ${HOME}/CodeBitBucket/strings/string_table.py ${S}/code/sundance/proxies/cloud/sundance_proxies
				cp ${HOME}/CodeBitBucket/strings/string_table.py ${S}/code/sundance/proxies/python/sundance_proxies
			fi
	        ${S}/scripts/generate_all_proxies.py --generate_string_table
			if [ ${LOCK_PORTS} ] ; then
				bbnote "Found ${LOCK_PORTS} - locking daemon servers"
				python3 ${S}/code/utils/lock_daemon_servers.py lock ${S}/code
			else
			    bbnote "Found ${LOCK_PORTS} - daemon servers remain unlocked"
			fi
	else
	        bbnote "This is NOT a buildbot build"
			if [ -e ${HOME}/CodeBitBucket/strings/string_table.py ]; then
				mkdir -p ${S}/code/sundance/proxies/cloud/sundance_proxies
				mkdir -p ${S}/code/sundance/proxies/python/sundance_proxies
				cp ${HOME}/CodeBitBucket/strings/string_table.py ${S}/code/sundance/proxies/cloud/sundance_proxies
				cp ${HOME}/CodeBitBucket/strings/string_table.py ${S}/code/sundance/proxies/python/sundance_proxies
			fi
	        ${S}/scripts/generate_all_proxies.py --generate_string_table --verbose
	fi

	# generate .pyc files
	python3 -O -mcompileall -b -x docs -d${INSTALL_DIR} ${S}/code

#	skip="false"
#	# running  only run test on build machine
#	if [ -z ${BUILD_BOT_BUILD} ] ; then
#		bbnote "Skip test and doc build"
#		skip="true"
#	else
#		export PYTHONPATH=${S}/code/framework
#		if [ -e /usr/local/bin/nosetests-3.3 ]; then
#			nosetests_bin="/usr/local/bin/nosetests-3.3"
#		elif [ -e /usr/local/bin/nosetests-3.2 ]; then
#			nosetests_bin="/usr/local/bin/nosetests-3.2"
#		else
#			nosetests_bin="nosetests"
#		fi
#
#	fi
#
#	if [ ${skip} = "false" ]; then
#		${S}/scripts/python_test_coverage.py --nosetests ${nosetests_bin}
#
#	   	# building documentation
#		make -C ${S}/docs html
#	fi
}

do_compile_append(){
    # remove __pycache__ dir
    find ${S}/code -type d -name __pycache__ | xargs rm -rf

    #set -x

    # Set a constant date in the header of every pyo, bytes 4,5,6 & 7
    # Mon Feb 16 14:00:00 MST 2015 = 0x54e25a50 = the date of build 2.0.0.12994 
    #d=$(printf 0x%x $(date --date='Mon Feb 16 14:00:00 MST 2015' +%s))
    #d0=$(printf "%02x\n" $(( $d & 0xff )) )
    #d1=$(printf "%02x\n" $(( $d >> 8 & 0xff )) )
    #d2=$(printf "%02x\n" $(( $d >> 16 & 0xff )) )
    #d3=$(printf "%02x\n" $(( $d >> 24 & 0xff )) )
    d0="50"
    d1="5a"
    d2="e2"
    d3="54"

    fixfile() {
        local f=$1
        echo ${d0}${d1}${d2}${d3} | xxd -r -p -s 4 - $f
    }

    for f in $(find ${S}/code -name *.pyo)
    do
        fixfile $f
    done

}

do_runstrip() {
	:
}

do_install () {
        # Install init.d scripts
	install -d ${D}/${sysconfdir}/init.d/
	cp -a ${S}/config/init.d/* ${D}/${sysconfdir}/init.d/
}

do_install_append() {
	# remove all .py file
	find ${D}/${INSTALL_DIR} -name proxies -prune -o -name *.py -print | xargs rm -f
	find ${D}/${INSTALL_DIR} -name *.py | xargs rm -f
	# remove yaml_definitions
	find ${D}/${INSTALL_DIR} -name yaml_definitions | xargs rm -rf
}

pkg_postinst_${PN} () {
#!/bin/sh -e
# Post install to make sure we have the correct setup 
#
if [ x"$D" = "x" ]; then
     logging()
     {
        if busybox ps | grep psplash | grep -qv grep
        then
            psplash-write "MSG $*"
        fi
        echo $*
        if [ ! -d /media/extra/update ]
        then
        mkdir -p /media/extra/update
        fi
        echo $(date) $* >> /media/extra/update/.firmware_update_status
        logger $*
     }
     
    if [ ! -d /media/extra/log/error ]
    then
        logging "Creating log directory..."
        mkdir -p /media/extra/log/error
    fi

    if [ -d /media/extra/log/error ]
    then
        logging "Removing all error log in /media/extra/log..."
        rm -f /media/extra/log/*error.log*
    fi
else
    exit 1
fi
}

PACKAGE_ARCH = "${MACHINE_ARCH}"

PACKAGES = " \
	${PN}-345d          \
    ${PN}-rf915d        \
	${PN}-framework     \
	${PN}-multiplexerd          \
	${PN}-mmpd          \
	${PN}-audmgrd   \
	${PN}-roubaix-services  \
	${PN}-initpumpernickel \
	${PN}-qml-framework     \
	${PN}-qml-framework-images-controls 	\
	${PN}-qml-framework-images-controls-keyboards 	\
	${PN}-qml-framework-images-controls-shared \
	${PN}-adtd          \
	${PN}-cloudd          \
	${PN}-dbapd        \
	${PN}-favicon \
	${PN}-global-conf \
	${PN}-httpd \
	${PN}-iod   \
	${PN}-launcherd \
	${PN}-modemd          \
	${PN}-netd  \
	${PN}-pcamd        \
	${PN}-procmand \
	${PN}-pyftpd \
	${PN}-rtspd   \
	${PN}-ssdpd      \
	${PN}-sundance-proxies      \
        ${PN}-zwaved-proxies \
	${PN}-sundance      \
	${PN}-test-daemon  \
	${PN}-test-ui  \
	${PN}-updated       \
	${PN}-usage-analytics \
	${PN}-utils \
	${PN}-videod    \
	${PN}-vocabulary    \
	${PN}-webd  \
	${PN}-zwaved        \
	${PN}-smarthomed-proxies      \
	${PN}-listenerd-proxies      \
	${PN}-smarthomed      \
	${PN}-listenerd      \
	${PN}-sound-wav-abs \
	${PN}-sound-wav-ad \
	${PN}-sound-wav-beeps \
	${PN}-sound-wav-dtmf \
	${PN}-sound-wav-pauses \
	${PN}-sound-wav-vocab	\
	${PN}-bootsplash	\
	${PN} \
"
