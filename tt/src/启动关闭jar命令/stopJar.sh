#!/bin/bash
echo "输入jar包的名字"

read jarname

echo "执行 kill -9 $(ps -ef | grep ${jarname} | awk '{if(NR==1) print $2}')"

kill -9 $(ps -ef | grep ${jarname} | awk '{if(NR==1) print $2}')

echo "执行完成"
