1. 使用`setnx`，若key不存在才会获取到锁
2. 使用lua脚本保证多个redis操作的原子性
3. 需要一个额外的看门狗定时任务来监听锁是否需要续约
4. 考虑redis节点宕机的情况，需要采用红锁的方式同时向N/2+1个节点申请锁，都申请到了才算获取成功，其中某个redis节点挂掉了，锁也不会被其他节点获取到
