#!/bin/sh
export PYTHONUNBUFFERED=1
/usr/bin/python3 /usr/bin/get_mesh_info.py &> /dev/null
/usr/bin/python3 /usr/bin/build_dot_graph.py &> /dev/null

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
