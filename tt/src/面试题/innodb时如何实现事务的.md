1. 执行修改语句前，先找到该语句所在的页，并将该页缓存在buffer pool中
2. 执行修改语句，就是修改buffer pool的数据
3.  根据修改语句生成一个redolog对象，并存入logbuffer中
4. 根据修改语句生成一个undolog对象
5. 如果事务提交，把redolog对象进行持久化，后续还有其他机制将buffer pool持久化到磁盘中
6. 如果事务回滚，则使用undolog