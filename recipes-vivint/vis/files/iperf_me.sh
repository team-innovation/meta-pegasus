#!/bin/sh

echo "test_me.sh got invoked with QUERY_STRING=$QUERY_STRING" >> /var/log/test_me.log
echo "and with REQUEST_METHOD=$REQUEST_METHOD" >> /var/log/test_me.log
echo "and with HTTP_REFERER=$HTTP_REFERER" >> /var/log/test_me.log

IFS='=&'
set -- $QUERY_STRING
src="$2"
dst="$4"
prot="$6"
secs="$8"
bw="${10}"
bw_units="${12}"
echo "test_me.sh src=$src, dst=$dst, prot=$prot, secs=$secs, bw=$bw, bw_units=$bw_units" >> /var/log/test_me.log

# TODO: validate src and dest:
# not equal
# represent real mesh nodes?

# TODO: fix use of /tmp/netv
info=`/usr/bin/python3 /opt/2gig/utils/send_net_router_command "netv miperf $src $dst $prot $secs $bw $bw_units"`
echo "info=$info" >> /var/log/test_me.log
if echo $info | grep "Connection refused"
then
    rate_data="iPerf client unable to connect to server"
else
    rate_data="$info"
fi
echo "$src|$dst|$prot|$secs|$bw|$bw_units" > /srv/www/vis/test/iperf_cmd.html
printf %s "$rate_data" | tail -n +3 > /srv/www/vis/test/iperf_result.html

echo "Content-type: text/html"
echo ""

echo '<html>'
echo '<head>'
echo '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">'
echo '<title>Network DOT Test</title>'
echo '</head>'
echo '<body>'
if [ "$REQUEST_METHOD" = "GET" ]
then
      echo "<meta http-equiv=\"refresh\" content=\"0; URL=$HTTP_REFERER\" />"
fi
echo '</body>'
echo '</html>'
exit 0
