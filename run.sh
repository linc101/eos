#!/bin/bash
pid=`ps aux | grep java | grep play | awk '{print $2}'`
if [ -n $pid ]; then
 kill -9 $pid
fi
echo "pid" $pid
play run --%uttp | /etc/colorlog
