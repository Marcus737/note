#!/bin/sh

stop_program() {
    pid="$1"
    stop_signal="$2"
    if [ "$stop_signal" = "" ]
    then
        stop_signal=15
    fi

    if [ "$pid" != "" ]
    then
        kill -$stop_signal $pid
        #检测是否正常kill
        #$? 最后运行的命令的结束代码（返回值）即执行上一个指令的返回值 (显示最后命令的退出状态。0表示没有错误，其他任何值表明有错误)
        if [ $? -ne 0 ]
         then
                echo "server pid: $pid is not running now"
                exit
        fi
        #等待进程退出 10s
        count=20
        systemPid=`ps $pid | grep $pid | awk '{print $1}'`
        while [ "$systemPid" != "" ] && [ $count -gt 0 ]
        do
            let count--
            systemPid=`ps $pid | grep $pid | awk '{print $1}'`
            sleep 0.5
        done

        #检查进程是否退出
        if [ "$systemPid" = "" ]
        then
                echo "server pid: $pid is stop ok"
        else
                echo "server pid: $pid is not exit in 10s, please check it"
            return 2
        fi
    else
        echo "server pid: $pid is not running now"
    fi

    return 0
}
# 先从文件读入pid
pid=`cat centerServer.pid`
# 调用方法，传入pid $1 是第一个参数， 默认执行kill -15 pid
stop_program $pid
