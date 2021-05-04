### NOTE ###
# When adding a new package to this recipe, create an inc file then add the require line
# in the inc file, you can have any prepend and append function, init script and postinst
# do_install_append is use to copy all the files to it right location for packaging
# last thing is to add the new package name to the PACKAGES = in this file.
####

DESCRIPTION = "Vivint Touchlink Apps"
LICENSE = "CLOSED"
require touchlink-apps-audmgrd.inc
require touchlink-apps-sundance.inc

require touchlink-apps-baguette.inc
require touchlink-apps-rtspd.inc
require touchlink-apps-videod.inc

require touchlink-apps-launcherd.inc

# fcc test apps
require touchlink-apps-test-ui.inc
require touchlink-apps-test-daemon.inc

require touchlink-apps-pcamd.inc
require touchlink-apps-iod.inc
require touchlink-apps-345d.inc
require touchlink-apps-cloudd.inc
require touchlink-apps-modemd.inc

# adtd is touchlink dependent
#require touchlink-apps-adtd.inc

require touchlink-apps-updated.inc
require touchlink-apps-usage-analytics.inc
require touchlink-apps-zwaved.inc
require touchlink-apps-netd.inc
require touchlink-apps-utils.inc
require touchlink-apps-httpd.inc
require touchlink-apps-huei.inc
require touchlink-apps-ssdpd.inc
require touchlink-apps-webd.inc
require touchlink-apps-dbapd.inc
require touchlink-apps-procmand.inc
require touchlink-apps-favicon.inc
require touchlink-apps-global-conf.inc
require touchlink-apps-flashpolicyd.inc
require touchlink-apps-mmpd.inc

#roubaix
require touchlink-apps-roubaix-services.inc
require touchlink-apps-initpumpernickel.inc

# framework
require touchlink-apps-framework.inc
require touchlink-apps-qml-framework.inc
require touchlink-apps-qml-framework-images.inc    

# Sound stuffs

require touchlink-apps-sounds-wav-chimes.inc

# pyftpd daemon
require touchlink-apps-pyftpd.inc

DISTRO_PR = ".1"

PR = "ml122"
PV = "1.0.0+git${SRCPV}"

SRCREV = "${GIT_APPS_REV}"
SRCBRANCH = "${GIT_APPS_BRANCH}"

GIT_APPS_SERVER ?= "${GIT_SERVER}"
GIT_APPS_PROTOCOL ?= "ssh"
GIT_STRINGS_SERVER ?= "/home/localRepos/constants/boilerplate/python"

SRC_URI = "git://${GIT_APPS_SERVER}/${GIT_APPS_TAG};protocol=${GIT_APPS_PROTOCOL};branch=${SRCBRANCH} \
    file://procman.d \
    file://procman.d-fcc \
    file://favicon.ico \
    file://updated.logrotate \
    file://segfault.logrotate \
    "

S = "${WORKDIR}/git"

PYTHON_BASEVERSION = "3.5"
PREFERRED_VERSION_python3 = "3.5.3"
PREFERRED_VERSION_python-native = "3.5.3"
PREFERRED_VERSION_iotivity = "2.0.0"

inherit autotools update-rc.d python3-dir pythonnative

RSYNC_CMD = "rsync -azv --exclude=.svn --exclude=test --exclude=.coverage --exclude=_coverage --exclude=_user_conf \
	     --exclude=.pytest_cache"

FRAMEWORK_DIR = "${PYTHON_SITEPACKAGES_DIR}"
INSTALL_DIR = "/opt/2gig"


#DEPENDS_REMOVED += "gst-rtsp-server # no clue

DEPENDS = " \
	gstreamer1.0-plugins-bad \
	gstreamer1.0-plugins-base \
	gstreamer1.0-plugins-good \
	gstreamer1.0 \
	python3-setuptools-native \
	python3-aiodns-native \
	python3-aioconsole-native \
	python3-aiohttp-native \
	python3-aiohttp-security-native \
	python3-aiohttp-session-native \
	python3-aiohttp-sse-native \
	python3-async-generator-native \
	python3-async-timeout-native \
	python3-atomicwrites-native \
	python3-attrs-native \
	python3-bcrypt-native \
	python3-bson-native \
	python3-cachetools \
	python3-cachetools-native \
	python3-cchardet-native \
	python3-cherrypy-native \
	python3-cstruct-native \
	python3-coverage-native \
	python3-certifi-native \
	python3-cython-native \
	python3-dateutil-native \
	python3-gnupg-native \
	python3-grpcio-native \
	python3-pycares-native \
	python3-multidict-native \
	python3-terminaltables-native \
	python3-idna-native \
	python3-idna-ssl-native \
	python3-grpcio-tools-native \
	python3-googleapis-common-protos-native \
	python3-intelhex-native \
	python3-jinja2-native \
	python3-jsonschema-native \
	python3-markupsafe-native \
	python3-mixpanel-native \
	python3-mock-native \
	python3-more-itertools-native \
	python3-msgpack-native \
	python3-native \
	python3-pathlib2-native \
	python3-paho-mqtt-native \
	python3-pexpect-native \
	python3-phue-native \
	python3-pluggy-native \
	python3-protobuf-native \
	python3-ptyprocess-native \
	python3-psutil-native \
	python3-pyalsaaudio-native \
	python3-pycrypto-native \
	python3-pyftpdlib-native \
	python3-pytest-native \
	python3-pytest-asyncio-native \
	python3-pytest-cov-native \
	python3-pyinotify-native \
	python3-pyserial-native \
	python3-pysodium-native \
	python3-py-native \
	python3-pyjwt-native \
	python3-pytz-native \
	python3-requests-native \
	python3-requests-toolbelt-native \
	python3-easydict-native \
	python3-six-native \
	python3-setproctitle-native \
	python3-soco-native \
	python3-sparsedict-native \
	python3-sentry-sdk-native \
	python3-toolz-native \
	python3-transitions-native \
	python3-typing-extensions-native \
	python3-uvloop-native \
	python3-wheel-native \
	python3-urllib3-native \
	python3-xmltodict-native \
	python3-yarl-native \
	libsodium-native \
	python3-pyopenssl-native \
	python3-cryptography-native \
	python3-cffi-native \
	python3-asn1crypto-native \
	python3-pycparser-native \
	breakpad \
	variant-lite \
	taocpp-json \
	zipgateway \
	zware \
	iotivity \
	zwave-nvm-converter \
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
	python3-sparsedict \
	python3-phue \
 	iotivity-resource \
 	iotivity-bridging-plugins \
    zwave-nvm-converter \
 	breakpad \
"

do_compile() {
    export HAS_BREAKPAD
    # Build hue plugin
    cd ${S}/code/sundance/plugins/hue
    oe_runmake STATIC_LIB_DIR=${STAGING_DIR_TARGET}/usr/lib HAS_BREAKPAD=TRUE
    # Build nest plugin
    cd ${S}/code/sundance/plugins/nest
    oe_runmake STATIC_LIB_DIR=${STAGING_DIR_TARGET}/usr/lib HAS_BREAKPAD=TRUE

    export PYTHONPATH=${S}/code/sundance/services/devices/generated/grpc:$PYTHONPATH

        #verify that the syntax for all JSON files in embedded-apps is correct
        set +e
        json_files=$(find ${S} -name "*.json")
        any_json_syntax_failures="no"
        for json_file in ${json_files[@]}; do
                json_verify_message=$(python3 ${S}/code/utils/verify_json_syntax.py --json-file=$json_file)
                json_verify_error_flag=$?
                if [ "$json_verify_error_flag" = "1" ]; then
                        echo $json_verify_message
                        any_json_syntax_failures="yes"
                elif [ ! "$json_verify_error_flag" = "0" ]; then
                        set -e
                        echo $json_verify_message
                        exit $json_verify_error_flag
                fi
        done
        set -e
        if [ "$any_json_syntax_failures" = "yes" ]; then
                exit 1
        fi


	if [ ${LOCK_PORTS} ] ; then
		bbnote "Found setting to LOCK_PORTS - locking daemon servers"
		python3 ${S}/code/utils/lock_daemon_servers.py lock ${S}/code
	else
	    bbnote "daemon servers remain unlocked"
	fi
	if [ ${REDUCE_LOGS} ] ; then
		bbnote "Found setting to REDUCE_LOGS - seting log level to INFO"
		python3 ${S}/code/utils/tune_logging_for_release.py INFO ${S}/code
	else
	    bbnote "logging remains verbose"
	fi

	# generate proxies
	if [ ${UPDATE_STRING_TABLE} ] ; then
			bbnote "This is a buildbot build"
		        CAMERA_PROTOBUF_BRANCH=${CAMERA_PROTOBUF_BRANCH} CAMERA_PROTOBUF_SERVER=${CAMERA_PROTOBUF_SERVER} ${S}/scripts/generate_all_proxies.py --allow-new-entries
	else
	        bbnote "This is NOT a buildbot build"
	        CAMERA_PROTOBUF_BRANCH=${CAMERA_PROTOBUF_BRANCH} CAMERA_PROTOBUF_SERVER=${CAMERA_PROTOBUF_SERVER} ${S}/scripts/generate_all_proxies.py --verbose
	fi

	# generate .pyc files
	python3 -O -mcompileall -b -x docs -d${INSTALL_DIR} ${S}/code

	skip="false"
	if [ ${BY_PASS_UNIT_TEST} ] ; then
		bbnote "Skip test and doc build"
		skip="true"
	else
		export PYTHONPATH=${STAGING_DIR}/${BUILD_SYS}/usr/lib/${PYTHON_DIR}/site-packages:$PYTHONPATH
	fi

	if [ ${skip} = "false" ]; then
		set -x
		${S}/scripts/python_test_coverage.py

	   	# building documentation
		#make -C ${S}/docs html
	fi
}

do_compile_append(){
    # remove __pycache__ dir
    find ${S}/code -type d -name __pycache__ | xargs rm -rf

    #set -x

    # Set a constant date in the header of every.pyc, bytes 4,5,6 & 7
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

    for f in $(find ${S}/code -name *.pyc)
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
    install -m 0755 ${S}/config/init.d/* ${D}/${sysconfdir}/init.d/
}

do_install_append() {
	# remove all .py file
	find ${D}/${INSTALL_DIR} -name proxies -prune -o -name *.py -print | xargs rm -f
	find ${D}/${INSTALL_DIR} -name *.py | xargs rm -f
	# remove yaml_definitions
	find ${D}/${INSTALL_DIR} -name yaml_definitions | xargs rm -rf

    # remove unused init scripts
    rm -f ${D}/${sysconfdir}/init.d/bootsplash.sh
    rm -f ${D}/${sysconfdir}/init.d/takeoverd
    rm -f ${D}/${sysconfdir}/init.d/zwaved
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

# Make sure the proxies are in the list before their non-proxy counterparts
# otherwise we end up with empty proxy packages and the build will fail
PACKAGES = " \
	${PN}-345d          \
	${PN}-framework     \
	${PN}-mmpd          \
	${PN}-audmgrd   \
	${PN}-roubaix-services  \
	${PN}-initpumpernickel \
    ${PN}-baguette \
	${PN}-qml-framework     \
	${PN}-qml-framework-images-controls 	\
	${PN}-qml-framework-images-controls-keyboards 	\
	${PN}-qml-framework-images-controls-shared \
	${PN}-adtd          \
	${PN}-cloudd          \
	${PN}-dbapd        \
	${PN}-favicon \
	${PN}-flashpolicyd   \
	${PN}-global-conf \
	${PN}-httpd \
	${PN}-huei \
	${PN}-iod   \
	${PN}-launcherd \
	${PN}-modemd-proxies      \
	${PN}-modemd          \
	${PN}-netd  \
	${PN}-pcamd        \
	${PN}-procmand \
	${PN}-pyftpd \
	${PN}-rtspd   \
	${PN}-ssdpd      \
	${PN}-sundance-proxies      \
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
	${PN}-sound-wav-chimes \
	${PN} \
"
