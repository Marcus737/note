# RDB
https://blog.csdn.net/qq_26222859/article/details/124027095
Redis DataBase。
指定的时间间隔后，将内存的数据写入磁盘
## 优点
- 只包含一个文件dump.rdb，方便持久化
- 性能好，fork了一个进程完成写操作，不影响主进程
## 缺点
- 保障性低，若redis在时间间隔内发送故障，那么数据就会丢失
## 触发
### 手动触发
- save 阻塞直至rdb完成
- bgsave fork进程进行rdb操作，只在fork时暂停一下，不阻塞
### 自动触发
- save m n 在m秒内有n个键发送改变
- flashall 清空所有数据前进行rdb操作
- 主从同步


# AOF
Append Only File
以日志形式记录每一个写，删除操作。查询操作不会记录
## 优点
- 数据安全 每秒同步，每修改同步和不同步三种策略。都是异步
- 通过append写文件，宕机恢复后也不会覆盖原理的内容，redis-check-aof解决数据一致性问题
- 提供rewrite机制，定期对aof文件进行重写，达到压缩的目的
## 缺点
- AOF比RDB文件大，且恢复慢
- 运行效率没有RDB高

# 对比
对数据安全要求高就用AOF。对性能要求高就用RDB.
若两个都开启，优先AOF