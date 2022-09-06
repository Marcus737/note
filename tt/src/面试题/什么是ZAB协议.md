# 是什么？
是zookeeper实现一致性的的协议
# 过程
1. 选举leader阶段： 从zookeeper集群中选一个leader，leader负责所有写操作
2. 数据同步阶段： 如有数据不一致，leader与节点进行数据同步
3. 请求广播阶段： 收到写请求时，利用两阶段提交来广播写请求