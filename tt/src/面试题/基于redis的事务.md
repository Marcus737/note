# 开启事务
发送`multi`命令，标识着事务的开始，会打开客户端的flags属性的REDIS_MULTI标识
# 命令入队
若发送的命令为`exec discard watch multi`的其中一个，会立马执行；
对于其他命令，会先检查该命令的语法是否正确，不正确会关闭flags属性的REDIS_MULTI标识并返回错误信息，否则放入一个事务队列，然后返回`QUEUED`回复
# 事务执行
发送`exec`命令，服务端提交事务。
- 如果flags属性不包含REDIS_MULTI标识，或者包含REDIS_DIRTY_CAS或REDIS_DIRTY_EXEC标识，会直接取消事务的执行。
- 执行队列中的事务，并将结果返回客户端

# 注意
- redis不支持事务回滚

# 命令
## WATCH
监控key，当key发生改变，之后的事务不会执行
## MULTI
开启事务，后续的命令放入队列
## EXEC
执行队列的命令，并返回结果，操作被打断时，返回nil
## DISCARD
清空事务队列并退出事务状态
## UNWATCH
取消监控key
