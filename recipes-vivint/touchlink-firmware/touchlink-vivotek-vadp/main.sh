#!/bin/sh

cmd=${1}
pkgpath=${2}
new_package=${3}
prog="app_vencjpeg"
binpath="${pkgpath}/${prog}"
event_task_dir="/etc/conf.d/event/task"

_Log()
{
	if [ -z "${SCRIPT_NAME}" ]; then
		echo "${1}"
	else
		echo "<script language='javaScript'>document.write('${1}')</script><br>"
	fi
}

case ${cmd} in
	start)
		echo "Creating new motion and ring event pipe for VADP use"
		cp ${pkgpath}/motion_vadp_event_0_0.xml ${event_task_dir}
		cp ${pkgpath}/ring_on_vadp_event.xml ${event_task_dir}
		killall -SIGUSR1 eventmgr
		#start-stop-daemon --start --quiet --background --name $prog --exec $binpath
		/usr/sbin/respawndog register $binpath > /dev/null
		echo "."
		;;
	stop)
		/usr/sbin/respawndog deregister $binpath
		#killall ${prog} 
		#start-stop-daemon -K --signal 9 --quiet --name $prog
		echo "."
		;;
	install)
		cp ${pkgpath}/${prog} ${pkgpath}/${prog}
		rm -rf ${pkgpath}/${prog}.*
		;;
	upgrade)
		# Optional. Doing upgrade
		_Log "Upgrading VADP package"
		rm -rf ${pkgpath}
		mv ${new_package} ${pkgpath}
		;;
	*)
		;;
esac

exit 0

