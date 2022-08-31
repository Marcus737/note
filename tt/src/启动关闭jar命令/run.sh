# $0：脚本自己的路径
# exex -a 忽略第一个参数
# "java($(cd `dirname $0`; pwd))" 打印脚本的目录
# $JAVA_HOME/bin/java 相当于java
# -server  选择server VM
# -Xmx256m 堆内存最大值256m
# -XX:MaxDirectMemorySize NIO的DirectBuffer 的最大大小 为256m
# -verbose:gc 在控制台输出gc信息
# -Djava.awt.headless=true 无显示
# -classpath 加载指定目录下的jar包
# com.icefire.center.CenterServer 指定的启动类
# >> stdout.log 追加写入日志
# 2>&1 将标准错误输出重定向到标准输出

exec -a "java($(cd `dirname $0`; pwd))" $JAVA_HOME/bin/java -server -Xmx256m -XX:MaxDirectMemorySize=256M -verbose:gc -Djava.awt.headless=true -classpath .:$CLASSPATH:../lib/*:../serverLib/*:centerServer.jar com.icefire.center.CenterServer >> stdout.log 2>&1 &

# $! 查询并保存最近运行的进程的pid
echo $! > centerServer.pid