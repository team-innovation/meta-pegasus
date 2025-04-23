### NOTE ###
# When adding a new package to this recipe, create an inc file then add the require line
# in the inc file, you can have any prepend and append function, init script and postinst
# do_install_append is use to copy all the files to it right location for packaging
# last thing is to add the new package name to the PACKAGES = in this file.
####

SUMMARY = "Vivint Touchlink Apps"
DESCRIPTION = "Vivint Python daemons for security panel."
LICENSE = "CLOSED"

PR = "ml131"
PV = "1.0.0+git${SRCPV}"

SRCREV = "${GIT_APPS_REV}"
SRCBRANCH = "${GIT_APPS_BRANCH}"

GIT_APPS_SERVER ?= "${GIT_SERVER}"
GIT_APPS_PROTOCOL ?= "ssh"
GIT_STRINGS_SERVER ?= "/home/localRepos/constants/boilerplate/python"
GIT_APPS_REPO ?= "embedded_apps"

inherit externalsrc systemd

EXTERNALSRC = "${TOPDIR}/../../../../../../embedded-apps"
SRC_URI = ""

S = "${WORKDIR}/git"

RSYNC_CMD = "rsync -azv --no-o --no-g --exclude=.git --exclude=test --exclude=.coverage --exclude=_coverage \
             --exclude=_user_conf --exclude=.pytest_cache"

INSTALL_DIR = "/opt/2gig"

do_compile() {
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

	# generate .pyc files
	python3 -OO -m compileall -x '/docs/|/test/' -d${INSTALL_DIR} ${S}/code

    # remove __pycache__ dir
    find ${S}/code -type d -name __pycache__ | xargs rm -rf
}

# Make sure the proxies are in the list before their non-proxy counterparts
# otherwise we end up with empty proxy packages and the build will fail
PACKAGES = ""
