#!/bin/bash
pid=`ps aux | grep java | grep play | awk '{print $2}'`
kill -9 $pid
echo $pid
play run --%uttp | /etc/colorlog
