#!/bin/bash
echo "输入jar包的位置"

read jarpath

echo "执行 nohup java -jar ${jarpath} > ${jarpath/\.jar/}.log 2>&1 &"

nohup java -jar ${jarpath} > ${jarpath/\.jar/}.log 2>&1 &

echo "执行完成"
