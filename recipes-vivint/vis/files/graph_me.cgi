#!/bin/sh
BASE_DOT_FILE=dot.svg
LOG_DIR=/var/log

export PYTHONUNBUFFERED=1
RUNNING=/tmp/graph_me.run
if [ ! -e $RUNNING ] ; then
    logger -t DEBUG "Run graph_me!"
    date > $RUNNING
    /usr/bin/python3 /usr/bin/get_mesh_info.py &> /var/log/get_mesh_info.log
    /usr/bin/python3 /usr/bin/build_dot_graph.py &> /var/log/build_dot_graph.log
    rm $RUNNING
    logger -t DEBUG "Done graph_me!"
    [ -e /usr/bin/dot ] && {
    	[ ! -e "/usr/lib/graphviz/config6" ] && {
		logger -t DEBUG "graph_me.cgi: Initialize dot config"
		/usr/bin/dot -c
	}
	WWW_DIR="/srv/www/network"
	[ -e "$WWW_DIR" ] && {
		logger -t DEBUG "graph_me.cgi: Build dot files"
		/usr/bin/dot -T svg $WWW_DIR/vis/test/tmp2.dot -o $WWW_DIR/$BASE_DOT_FILE
		/usr/bin/dot -T svg $WWW_DIR/vis/test/tmp3.dot -o $WWW_DIR/mesh_$BASE_DOT_FILE
		# backup to /var/log so we can download them when we request logs
		tar -czf $LOG_DIR/node_map.tar.gz $WWW_DIR/$BASE_DOT_FILE $WWW_DIR/mesh_$BASE_DOT_FILE $WWW_DIR/vis/test/tmp2.dot $WWW_DIR/vis/test/tmp3.dot $WWW_DIR/tmp*.dat
	}
    }
else
    logger -t DEBUG "graph_me is already running!"
    while [ -e $RUNNING ] ; do
	sleep 1
    done
fi

echo "Content-type: text/html"
echo ""

echo '<html>'
echo '<head>'
echo '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">'
echo '<title>Hello World</title>'
echo '</head>'
echo '<body>'
if [ "$REQUEST_METHOD" = "GET" ]
then
      echo "<meta http-equiv=\"refresh\" content=\"0; URL=$HTTP_REFERER\" />"
fi
echo '</body>'
echo '</html>'

exit 0
